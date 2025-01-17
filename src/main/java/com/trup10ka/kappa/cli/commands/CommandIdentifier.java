package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.Nullable;

public enum CommandIdentifier
{

    /* --- Show commands --- */
    SHOW_ALL_CUSTOMERS_ORDERS("sao"),

    /* --- Create commands --- */
    INSERT_CUSTOMER("cc"),
    INSERT_ORDER("co"),

    /* --- Delete commands --- */
    DELETE_ORDER("do"),
    DELETE_CUSTOMER("dc"),

    /* --- Simulation commands --- */
    SIMULATE_DIRTY_READ("sdr"),
    SIMULATE_DIRTY_WRITE("sdw"),

    /* --- Export/Import commands --- */
    EXPORT_MOST_ORDERED_PRODUCT("ep"),
    IMPORT_CUSTOMERS("ic"),

    /* --- Util commands --- */
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
