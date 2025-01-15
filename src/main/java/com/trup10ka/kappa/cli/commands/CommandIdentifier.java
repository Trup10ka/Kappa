package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.Nullable;

public enum CommandIdentifier
{

    INSERT_CUSTOMER("cc"),
    INSERT_ORDER("co"),
    DELETE_ORDER("do"),
    DELETE_CUSTOMER("dc"),
    SIMULATE_DIRTY_READ("sdr"),
    SIMULATE_DIRTY_WRITE("sdw"),
    SET_ISOLATION_LEVEL("sil"),
    HELP("help"),
    EXIT("exit");

    public final String identifier;

    CommandIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    @Nullable
    public static CommandIdentifier fromString(String identifier)
    {
        for (CommandIdentifier commandIdentifier : CommandIdentifier.values())
        {
            if (commandIdentifier.identifier.equals(identifier))
            {
                return commandIdentifier;
            }
        }

        return null;
    }
}
