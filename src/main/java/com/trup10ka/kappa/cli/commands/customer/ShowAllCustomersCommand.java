package com.trup10ka.kappa.cli.commands.customer;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShowAllCustomersCommand extends Command
{

    private final CustomerService customerService;

    public ShowAllCustomersCommand(@NotNull CommandIdentifier identifier, CustomerService customerService)
    {
        super(identifier);
        this.customerService = customerService;
        setShortDescription("See all customers");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        if (args != null)
        {
            System.out.println("This command does not take any arguments");
            return "";
        }

        List<Customer> customers = customerService.getAllCustomers();

        return customers.stream()
                .map(Customer::toString)
                .reduce("", (acc, customer) -> acc + customer + "\n");
    }

    @Override
    public String getHelp()
    {
        return """
                ========================================================================================================
                sac - See all customers
                Usage:
                    sc
                ========================================================================================================
                """;
    }
}
