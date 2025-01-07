package com.trup10ka.kappa.cli.commands;

import org.jetbrains.annotations.NotNull;

public class ExitCommand extends Command
{
    public ExitCommand()
    {
        super(CommandIdentifier.EXIT);
    }

    @Override
    @NotNull
    public String execute()
    {
        System.out.println("Exiting...");
        System.exit(0);
        return null;
    }
}
