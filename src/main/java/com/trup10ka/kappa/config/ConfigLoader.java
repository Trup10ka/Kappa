package com.trup10ka.kappa.config;

import org.jetbrains.annotations.NotNull;

public interface ConfigLoader
{
    @NotNull
    KappaConfig loadConfig();
}
