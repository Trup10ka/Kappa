package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.NotNull;

public class DeleteTransactionCommand extends Command
{

    public DeleteTransactionCommand(CommandIdentifier identifier)
    {
        super(identifier);
    }

    @Override
    public @NotNull String execute()
    {
        return "Executed";
    }
}
