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
        setShortDescription("Exits the application");
    }

    @Override
    @NotNull
    public String execute(@Nullable String args)
    {
        System.out.println("Exiting...");
        System.exit(0);
        return null;
    }

    @Override
    public String getHelp()
    {
        return """
                ===================================================================================================================================================
                    exit - Exits the application
                
                    Usage: exit
                ===================================================================================================================================================
                """;
    }
}
