package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.NotNull;

public class CreateTransactionCommand extends Command
{

    public CreateTransactionCommand(CommandIdentifier identifier)
    {
        super(identifier);
    }

    @Override
    public @NotNull String execute()
    {
        return "Executed";
    }
}
