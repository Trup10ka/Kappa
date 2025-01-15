package com.trup10ka.kappa;

import com.trup10ka.kappa.cli.CliClient;
import com.trup10ka.kappa.config.ConfigLoader;
import com.trup10ka.kappa.config.FileConfigLoader;
import com.trup10ka.kappa.config.KappaConfig;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.HikariDbClient;
import com.trup10ka.kappa.exceptions.InvalidConfigException;
import org.jetbrains.annotations.NotNull;

public class KappaApp
{
    @NotNull
    private final ConfigLoader configLoader = new FileConfigLoader("config.conf");

    private CliClient cliClient;

    public void init()
    {
        KappaConfig config = configLoader.loadConfig();

        DbClient hikariDbClient = new HikariDbClient(config);
        hikariDbClient.init();
        initDatabase(hikariDbClient);

        cliClient = new CliClient(hikariDbClient);
    }

    public void run()
    {
        cliClient.start();
    }

    private void initDatabase(DbClient dbClient)
    {
        try
        {
            dbClient.initDatabase();
        }
        catch (InvalidConfigException e)
        {
            System.out.println("Failed to connect to the database. Exiting...");
            dbClient.close();
            System.exit(1);
        }
    }
}
