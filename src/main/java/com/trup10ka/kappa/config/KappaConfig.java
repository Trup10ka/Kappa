package com.trup10ka.kappa.config;

import org.jetbrains.annotations.NotNull;

public record KappaConfig(
        @NotNull String dbName,
        @NotNull String dbUser,
        @NotNull String dbPassword,
        @NotNull String dbHost
) {
}
