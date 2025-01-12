package com.trup10ka.kappa.db;

import com.trup10ka.kappa.exceptions.InvalidConfigException;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;

public interface DbClient
{
    void init();

    void testConnection() throws InvalidConfigException;

    @NotNull
    DataSource getDataSource();

    void close();
}
