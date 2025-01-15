package com.trup10ka.kappa.cli.commands.util;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExitCommand extends Command
{
    public ExitCommand()
    {
        super(CommandIdentifier.EXIT);
    }

    @Override
    @NotNull
    public String execute(@Nullable String args)
    {
        System.out.println("Exiting...");
        System.exit(0);
        return null;
    }
}
