package com.trup10ka.kappa.cli.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface CommandArgumentParser
{
    @NotNull
    Map<String, @Nullable String> parse(@NotNull String args, @NotNull String keyValueDelimiter);

    @NotNull
    Map<String, @Nullable String> parse(@NotNull String args);
}
