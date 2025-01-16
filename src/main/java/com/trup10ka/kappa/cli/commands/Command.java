package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Command
{
    @NotNull
    private final CommandIdentifier identifier;

    private String shortDescription;

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

    protected void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    @NotNull
    public String getShortDescription()
    {
        return shortDescription;
    }

    public abstract String getHelp();
}
