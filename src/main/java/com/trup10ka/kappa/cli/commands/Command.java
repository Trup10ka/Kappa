package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Command
{
    @NotNull
    private final CommandIdentifier identifier;

    public Command(@NotNull CommandIdentifier identifier)
    {
        this.identifier = identifier;
    }

    @NotNull
    public abstract String execute(@Nullable String args);

    @NotNull
    public CommandIdentifier getIdentifier()
    {
        return identifier;
    }
}
