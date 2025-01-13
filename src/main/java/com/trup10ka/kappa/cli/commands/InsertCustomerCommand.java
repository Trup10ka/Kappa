package com.trup10ka.kappa.cli.commands;

import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.CustomerSex;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class InsertCustomerCommand extends Command
{

    private final CustomerService customerService;

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

        String[] arguments = args.split(" ");
        Customer customer = parseCustomer(arguments);

        if (customer == null)
        {
            return "Customer not inserted, client error";
        }

        if (customer.customerCredits() == -1)
        {
            return "Invalid credits format";
        }

        int customerId = customerService.addCustomer(customer.firstName(), customer.lastName(), customer.sex(), customer.customerCredits());

        if (customerId == -1)
        {
            return "Failed to insert customer, DB error";
        }

        return "Customer inserted successfully, id of the newly inserted customer: " + customerId;
    }

    @Nullable
    private Customer parseCustomer(String[] args)
    {
        if (args.length < 3)
        {
            return null;
        }

        String firstName = args[0];
        String lastName = args[1];
        CustomerSex sex = parseSex(args[2]);
        int credits = parseCredits(args);

        if (!checkIfAllMandatoryFieldsArePresent(firstName, lastName, sex))
            return null;


        return new Customer(
                firstName,
                lastName,
                sex,
                credits
        );
    }

    @Nullable
    private CustomerSex parseSex(String sexAsString)
    {
        try
        {
            return CustomerSex.valueOf(sexAsString);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Sex was not received in a valid format, possible values are: " + Arrays.toString(CustomerSex.values()));
        }
        return null;
    }

    private int parseCredits(String[] args)
    {
        int credits = 0;
        try
        {
            credits = Integer.parseInt(args[3]);
            return credits;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("No credits provided, setting to default value of 0");
            return credits;
        }
        catch (NumberFormatException e)
        {
            System.out.println("Credits are not in a valid integer format");
            return -1;
        }
    }

    private boolean checkIfAllMandatoryFieldsArePresent(String firstName, String lastName, CustomerSex sex)
    {
        boolean ret = true;

        if (firstName == null)
        {
            System.out.println("First name not provided");
            ret = false;
        }

        if (lastName == null)
        {
            System.out.println("Last name not provided");
            ret = false;
        }

        if (sex == null)
        {
            System.out.println("Sex not provided");
            ret = false;
        }

        return ret;
    }
}
