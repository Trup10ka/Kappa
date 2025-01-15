package com.trup10ka.kappa.cli.commands.customer;

import com.trup10ka.kappa.cli.arguments.CommandArgumentParser;
import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.CustomerSex;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;

public class InsertCustomerCommand extends Command
{

    private final CustomerService customerService;

    private final CommandArgumentParser argumentParser = new StrictPairArgumentParser(new String[] {"-fn", "-ln", "-s", "-c"} );

    public InsertCustomerCommand(CommandIdentifier identifier, CustomerService customerService)
    {
        super(identifier);
        this.customerService = customerService;
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        if (args == null)
        {
            return "No arguments provided";
        }

        Map<String, String> parsedArguments = argumentParser.parse(args);
        if (parsedArguments.isEmpty())
        {
            return "Failed to parse arguments";
        }

        Customer customer = parseCustomer(parsedArguments);
        if (customer == null)
        {
            return "Customer not inserted, client error";
        }

        if (customer.customerCredits() == -1)
        {
            return "Invalid credits format";
        }

        int customerId = customerService.addCustomer(customer);

        if (customerId == -1)
        {
            return "Failed to insert customer, DB error";
        }

        return "Customer inserted successfully, id of the newly inserted customer: " + customerId;
    }

    @Nullable
    private Customer parseCustomer(Map<String, String> parsedArguments)
    {
        String firstName = parsedArguments.get("-fn");
        String lastName = parsedArguments.get("-ln");
        CustomerSex sex = parseSex(parsedArguments.getOrDefault("-s", null));
        int credits = parseCredits(parsedArguments.get("-c"));

        if (!checkIfAllMandatoryFieldsArePresent(firstName, lastName, sex))
        {
            return null;
        }

        return new Customer(firstName, lastName, sex, credits);
    }

    @Nullable
    private CustomerSex parseSex(String sexAsString)
    {
        try
        {
            return CustomerSex.valueOf(sexAsString.toUpperCase());
        }
        catch (NullPointerException ignored)
        {}
        catch (IllegalArgumentException e)
        {
            System.out.println("Sex was not received in a valid format, possible values are: " + Arrays.toString(CustomerSex.values()));
        }
        return null;
    }

    private int parseCredits(String arg)
    {
        int credits;
        try
        {
            credits = Integer.parseInt(arg);
            return credits;
        }
        catch (NumberFormatException e)
        {
            System.out.println(" -c : Credits are not in a valid integer format or not provided");
            return -1;
        }
    }

    private boolean checkIfAllMandatoryFieldsArePresent(String firstName, String lastName, CustomerSex sex)
    {
        boolean ret = true;

        if (firstName == null)
        {
            System.out.println(" -fn : First name not provided");
            ret = false;
        }

        if (lastName == null)
        {
            System.out.println(" -ln : Last name not provided");
            ret = false;
        }

        if (sex == null)
        {
            System.out.println(" -s : Sex not provided");
            ret = false;
        }

        return ret;
    }
}
