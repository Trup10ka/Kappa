package com.trup10ka.kappa.cli.commands.customer;

import com.trup10ka.kappa.cli.arguments.CommandArgumentParser;
import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class DeleteCustomerCommand extends Command
{

    private final CustomerService customerService;

    private final CommandArgumentParser argumentParser = new StrictPairArgumentParser(new String[] { "-cid", "-fn", "-ln" } );

    public DeleteCustomerCommand(@NotNull CommandIdentifier identifier, @NotNull CustomerService customerService)
    {
        super(identifier);
        this.customerService = customerService;
        setShortDescription("Deletes a customer from the database");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        if (args == null || args.isEmpty())
            return "No arguments provided";

        Map<String, String> parsedArguments = argumentParser.parse(args);
        if (parsedArguments.isEmpty())
            return "Failed to parse arguments";

        if (parsedArguments.containsKey("-cid"))
        {
            boolean success = deleteWithId(parsedArguments);
            return success ? "Customer deleted successfully" : "Failed to delete customer";
        }
        else if (parsedArguments.containsKey("-fn") && parsedArguments.containsKey("-ln"))
        {
            boolean success = deleteWithName(parsedArguments);
            return success ? "Customer deleted successfully" : "Failed to delete customer";
        }
        else
        {
            return "Invalid arguments";
        }
    }

    @Override
    public String getHelp()
    {
        return """
               ===================================================================================================================================================
                    dc - Deletes a customer from the database.
               
                    Usage: dc -cid <customer_id>
                    OR
                    delete-customer -fn <first_name> -ln <last_name>
               ===================================================================================================================================================
               """;
    }

    private boolean deleteWithId(Map<String, String> parsedArguments)
    {
        try
        {
            int customerId = Integer.parseInt(parsedArguments.get("-cid"));
            return customerService.deleteCustomerById(customerId);
        }
        catch (NumberFormatException e)
        {
            System.out.println("The id you provided is not a number!");
            return false;
        }
    }

    private boolean deleteWithName(Map<String, String> parsedArguments)
    {
        String firstName = parsedArguments.get("-fn");
        String lastName = parsedArguments.get("-ln");
        return customerService.deleteCustomerByName(firstName, lastName);
    }
}
