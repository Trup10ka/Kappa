package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.Nullable;

public enum CommandIdentifier
{

    INSERT_CUSTOMER("cc"),
    INSERT_ORDER("co"),
    DELETE_ORDER("do"),
    DELETE_CUSTOMER("dc"),
    SET_ISOLATION_LEVEL("sil"),
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
