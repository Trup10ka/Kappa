package com.trup10ka.kappa.cli.commands.util;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Scanner;

public abstract class IsolationSecurityTestCommand extends Command
{

    protected final CustomerService customerService;

    protected final DbClient dbClient;

    protected final Scanner scanner = new Scanner(System.in);

    protected IsolationSecurityTestCommand(CommandIdentifier identifier, CustomerService customerService, DbClient dbClient)
    {
        super(identifier);
        this.customerService = customerService;
        this.dbClient = dbClient;
    }

    @Override
    @NotNull
    public abstract String execute(@Nullable String args);

    protected void askToRemoveTemplateCustomer(int customerId)
    {
        System.out.println("Removing template user...\n");
        char input = readUserInputForYorN();
        if (input == 'y')
        {
            boolean isRemoved = customerService.deleteCustomerById(customerId);
            System.out.println(isRemoved ? "Template user removed" : "Failed to remove template user");
        }
        else if (input == 'n')
        {
            System.out.println("Template user not removed");
        }
    }

    protected char readUserInputForYorN()
    {
        System.out.println("Remove template user? (y/n)");

        char input = scanner.nextLine().charAt(0);
        while (input != 'y' && input != 'n')
        {
            System.out.println("Enter [y] for yes or [n] for no");
            input = scanner.nextLine().charAt(0);
        }
        return input;
    }

    @Override
    public abstract String getHelp();
}
