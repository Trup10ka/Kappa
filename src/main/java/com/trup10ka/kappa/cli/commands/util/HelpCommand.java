package com.trup10ka.kappa.cli.commands.util;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class HelpCommand extends Command
{

    private List<Command> allCommands;

    public HelpCommand(CommandIdentifier commandIdentifier)
    {
        super(commandIdentifier);
    }

    public void initHelpCommand(@NotNull List<Command> allCommands)
    {
        this.allCommands = allCommands;
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        if (args == null || args.isEmpty())
            return assembleHelp();

        args = args.trim().split(" ")[0];
        for (Command command : allCommands)
        {
            if (command.getIdentifier().identifier.equals(args.toLowerCase()))
            {
                return command.getHelp();
            }
        }
        return "No command found";
    }

    @Override
    public String getHelp()
    {
        return """
                ===================================================================================================================================================
                    help - Prints help for all commands or a specific command
                
                    Usage: help <command_name>(optional)
                ===================================================================================================================================================
                """;
    }

    private String assembleHelp()
    {
        StringBuilder help = new StringBuilder();
        help.append("Available commands:\n");
        for (Command command : allCommands)
        {
            help.append("\t- ").append(command.getIdentifier().identifier).append("\n");
        }
        return help.toString();
    }
}
