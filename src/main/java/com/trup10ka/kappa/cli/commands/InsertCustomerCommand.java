package com.trup10ka.kappa.cli.commands;

import com.trup10ka.kappa.cli.CliClient;
import org.jetbrains.annotations.NotNull;

public class InsertCustomerCommand extends Command
{

    private final CliClient cliClient;

    public InsertCustomerCommand(CommandIdentifier identifier, CliClient cliClient)
    {
        super(identifier);
        this.cliClient = cliClient;
    }

    @Override
    public @NotNull String execute()
    {


        return "Customer inserted successfully";
    }
}
