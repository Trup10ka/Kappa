package com.trup10ka.kappa.cli.commands.util;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Scanner;

/**
 * Abstract class representing a command for performing isolation security tests.
 * <p>
 * This class extends {@link Command} and provides functionality to interact with the user for security tests,
 * specifically focusing on customer deletion and handling user input to remove a template customer from the system.
 * It utilizes the {@link CustomerService} to perform customer-related operations and {@link DbClient} to interact with the database.
 * </p>
 */
public abstract class IsolationSecurityTestCommand extends Command
{

    protected final CustomerService customerService;
    protected final DbClient dbClient;
    protected final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs an {@code IsolationSecurityTestCommand} with the given identifier, customer service, and database client.
     *
     * @param identifier The identifier for the command.
     * @param customerService The service responsible for handling customer operations.
     * @param dbClient The database client used to interact with the database.
     */
    protected IsolationSecurityTestCommand(CommandIdentifier identifier, CustomerService customerService, DbClient dbClient)
    {
        super(identifier);
        this.customerService = customerService;
        this.dbClient = dbClient;
    }

    /**
     * Executes the security test command with the provided arguments.
     * <p>
     * This method is intended to be implemented by subclasses to perform the specific logic of the command.
     * </p>
     *
     * @param args The arguments for executing the command.
     * @return A string result of the execution, typically used for displaying the outcome of the operation.
     */
    @Override
    @NotNull
    public abstract String execute(@Nullable String args);

    /**
     * Prompts the user to confirm whether they want to remove the template customer.
     * If the user confirms with 'y', the customer is removed using the {@link CustomerService}.
     * The result is printed to the console.
     *
     * @param customerId The ID of the customer to remove.
     */
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

    /**
     * Reads a 'y' or 'n' input from the user to confirm whether the template customer should be removed.
     * If the input is invalid, the user is repeatedly prompted until a valid response is received.
     *
     * @return The user's input character ('y' for yes, 'n' for no).
     */
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

    /**
     * Provides help information for this command.
     * <p>
     * This method is intended to be implemented by subclasses to describe the functionality and usage of the command.
     * </p>
     *
     * @return A string containing the help text for the command.
     */
    @Override
    public abstract String getHelp();
}
