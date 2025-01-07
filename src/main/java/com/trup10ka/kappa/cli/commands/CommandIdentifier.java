package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.Nullable;

public enum CommandIdentifier
{

    CREATE_TRANSACTION("create-t"),
    DELETE_TRANSACTION("delete-t"),
    COMMIT_TRANSACTION("commit-t"),
    ROLLBACK_TRANSACTION("rollback-t"),
    SET_ISOLATION_LEVEL("set-isolation-level"),
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
