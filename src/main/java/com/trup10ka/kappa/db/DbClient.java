package com.trup10ka.kappa.db;

import com.trup10ka.kappa.exceptions.InvalidConfigException;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;


public abstract class DbClient
{

    public abstract void init();

    public abstract void testConnection() throws InvalidConfigException;

    @NotNull
    public abstract DataSource getDataSource();

    public abstract void close();
}
