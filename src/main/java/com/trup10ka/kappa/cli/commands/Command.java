package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.NotNull;

public abstract class Command
{
    @NotNull
    private final CommandIdentifier identifier;

    public Command(@NotNull CommandIdentifier identifier)
    {
        this.identifier = identifier;
    }

    @NotNull
    public abstract String execute();

    @NotNull
    public CommandIdentifier getIdentifier()
    {
        return identifier;
    }
}
