package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.db.DbClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class DatabaseService
{
    protected final DbClient dbClient;

    protected DatabaseService(DbClient dbClient)
    {
        this.dbClient = dbClient;
    }
}
