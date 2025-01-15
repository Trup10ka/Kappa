package com.trup10ka.kappa.config;

import com.trup10ka.kappa.util.IsolationLevel;
import org.jetbrains.annotations.NotNull;

public record KappaConfig(
        @NotNull String dbName,
        @NotNull String dbUser,
        @NotNull String dbPassword,
        @NotNull String dbHost,
        @NotNull IsolationLevel dbIsolationLevel
)
{
    public String getDbUrl()
    {
        return String.format("jdbc:mysql://%s/%s", dbHost, dbName);
    }
}
