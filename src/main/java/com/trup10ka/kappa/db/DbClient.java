package com.trup10ka.kappa.db;

import com.trup10ka.kappa.db.services.ServiceManager;
import com.trup10ka.kappa.exceptions.InvalidConfigException;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;


public abstract class DbClient
{
    public abstract void init();

    public abstract void initDatabase() throws InvalidConfigException;

    public abstract void close();

    @NotNull
    public abstract DataSource getDataSource();

    @NotNull
    public abstract ServiceManager getServiceManager();
}
