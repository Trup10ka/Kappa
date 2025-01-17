package com.trup10ka.kappa.db;

import com.trup10ka.kappa.config.KappaConfig;
import com.trup10ka.kappa.db.services.ServiceManager;
import com.trup10ka.kappa.exceptions.InvalidConfigException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariDbClient extends DbClient
{
    @NotNull
    private final KappaConfig config;

    private HikariDataSource hikariDataSource;

    private final ServiceManager serviceManager = new ServiceManager(this);

    public HikariDbClient(@NotNull KappaConfig config)
    {
        this.config = config;
    }

    @Override
    public void init()
    {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(this.config.getDbUrl());
        config.setUsername(this.config.dbUser());
        config.setPassword(this.config.dbPassword());

        setupPoolRules(config);
        initHikariPool(config);
    }

    @Override
    public void initDatabase()
    {
        HikariConfig config = createNonPooledConfig();

        try (HikariDataSource hikariDataSource = new HikariDataSource(config);
                Connection connection = hikariDataSource.getConnection())
        {
            connection.setAutoCommit(false);
            initDbScheme(connection);
            connection.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        try (Connection connection = hikariDataSource.getConnection())
        {
            initViews(connection);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close()
    {
        hikariDataSource.close();
    }


    private void initHikariPool(HikariConfig config)
    {
        try
        {
            hikariDataSource = new HikariDataSource(config);
        }
        catch (Exception e)
        {
            System.out.println("Failed to initialize the Hikari pool. Reason: " + e.getMessage() + "\n");
            System.out.println("Possible reasons: \n1. Invalid database credentials\n2. Invalid database host\n3. Invalid database name \n");
            System.out.println("Look into config.conf - If none of the above reasons are applicable, please contact the administrator.");
            System.exit(0);
        }
    }

    private void setupPoolRules(HikariConfig config)
    {
        config.setMaximumPoolSize(8);
        config.setMinimumIdle(2);
        config.setTransactionIsolation(this.config.dbIsolationLevel().value);
        config.setConnectionTimeout(30000); // 30 seconds
        config.setMaxLifetime(1800000); // 30 minutes
    }

    private HikariConfig createNonPooledConfig()
    {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.config.getDbUrlWithoutDbName());
        config.setUsername(this.config.dbUser());
        config.setPassword(this.config.dbPassword());
        return config;
    }

    private String loadSchemeFromResource()
    {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("scheme.sql"))
        {
            if (inputStream == null)
            {
                System.out.println("Could not find scheme in resources, if there is no DB on the server, the program will crash!");
                return "SELECT 1";
            }

            return new String(inputStream.readAllBytes());
        }
        catch (IOException e)
        {
            System.out.println("Could not load scheme, if there is no DB on server, the program will crash!");
            return "SELECT 1";
        }
    }

    private void initDbScheme(Connection connection) throws SQLException
    {
        Statement initScheme = connection.createStatement();
        String[] batches = loadSchemeFromResource().split(";");
        for (String batch : batches)
        {
            if (batch.trim().toUpperCase().startsWith("CREATE OR REPLACE VIEW") || batch.isBlank())
            {
                continue;
            }
            initScheme.addBatch(batch);
        }
        initScheme.executeBatch();
    }

    private void initViews(Connection connection) throws SQLException
    {
        Statement initScheme = connection.createStatement();
        String[] batches = loadSchemeFromResource().split(";");
        for (String batch : batches)
            if (batch.trim().toUpperCase().startsWith("CREATE OR REPLACE VIEW"))
            {
                initScheme.executeUpdate(batch);
            }
    }

    @Override
    public @NotNull ServiceManager getServiceManager()
    {
        return serviceManager;
    }

    @Override
    @NotNull
    public DataSource getDataSource()
    {
        return hikariDataSource;
    }
}
