package com.trup10ka.kappa.db;

import com.trup10ka.kappa.config.KappaConfig;
import com.trup10ka.kappa.db.services.ServiceManager;
import com.trup10ka.kappa.exceptions.InvalidConfigException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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
    public void testConnection() throws InvalidConfigException
    {
        try (Connection connection = this.getDataSource().getConnection())
        {
            if (!connection.isValid(2))
            {
                throw new InvalidConfigException("Failed to validate the connection.");
            }
        }
        catch (SQLException e)
        {
            throw new InvalidConfigException("Failed to establish a connection. Reason: " + e.getMessage());
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
            System.out.println("If none of the above reasons are applicable, please check the logs for more information and contact the administrator.");
            System.exit(1);
        }
    }

    private void setupPoolRules(HikariConfig config)
    {
        config.setMaximumPoolSize(8);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000); // 30 seconds
        config.setMaxLifetime(1800000); // 30 minutes
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
