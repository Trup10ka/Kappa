package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DeleteTransactionCommand extends Command
{

    public DeleteTransactionCommand(CommandIdentifier identifier)
    {
        super(identifier);
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        return "Executed";
    }
}
