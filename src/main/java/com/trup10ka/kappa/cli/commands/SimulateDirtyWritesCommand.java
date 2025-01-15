package com.trup10ka.kappa.cli.commands;

import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.CustomerSex;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.util.Scanner;

import static com.trup10ka.kappa.util.ThreadUtil.sleepSeconds;

public class SimulateDirtyWritesCommand extends IsolationSecurityTestCommand
{

    private final Scanner scanner = new Scanner(System.in);

    public SimulateDirtyWritesCommand(@NotNull CommandIdentifier identifier, CustomerService customerService, DbClient dbClient)
    {
        super(identifier, customerService, dbClient);
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        System.out.println("By calling this command, a template user will be created to demonstrate dirty writes\n");
        sleepSeconds(4);
        System.out.println(
                """
                        When calling this command, the isolation level is set to READ_UNCOMMITED to demonstrate dirty reads and CANNOT BE CHANGED, otherwise dirty read cannot be simulated\s
                        Important note: Not all databases allow demonstration of dirty writes, because they do not enable turning off the row locking during transaction, \
                        so this command may not work on all databases
                        """
        );
        sleepSeconds(4);

        System.out.println("Creating a template user with 100 credits\n");

        Customer customer = new Customer("Template", "Customer", CustomerSex.OTHER, 100);
        int customerId = customerService.addCustomer(customer);

        System.out.println("Template user created with id: " + customerId + "\n");

        sleepSeconds(4);

        System.out.println("Starting first transaction to write to the user credits... \n");

        startDirtyWrite(customerId);

        askToRemoveTemplateCustomer(customerId);

        return "Dirty write completed";
    }

    private void startDirtyWrite(int customerId)
    {
        try (Connection firstWriteConnection = dbClient.getDataSource().getConnection())
        {
            firstWriteConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            firstWriteConnection.setAutoCommit(false);

            customerService.updateCustomer(customerId,
                    new Customer("Template", "Customer", CustomerSex.OTHER, 200));
            System.out.println("Transaction 1: Updated credits to 200\n");
            sleepSeconds(4);

            System.out.println("Starting second transaction to write the user credits... \n");
            sleepSeconds(4);

            try (Connection secondWriteConnection = dbClient.getDataSource().getConnection())
            {
                secondWriteConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                secondWriteConnection.setAutoCommit(false);

                customerService.updateCustomer(customerId,
                        new Customer("Template", "Customer", CustomerSex.OTHER, 300));
                System.out.println("Transaction 2: Updated credits to 300\n");
                sleepSeconds(4);

                System.out.println("Press enter to read current uncommited value...");
                scanner.nextLine();

                try (Connection readConnection = dbClient.getDataSource().getConnection())
                {
                    readConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    readConnection.setAutoCommit(false);

                    Customer customer = customerService.getCustomerById(customerId);
                    System.out.println("Current uncommited value: " + customer.customerCredits() + "\n");
                    sleepSeconds(4);
                }

                firstWriteConnection.commit();
                System.out.println("First transaction committed\n");
                sleepSeconds(4);

                secondWriteConnection.commit();
                System.out.println("Second transaction committed\n");
                sleepSeconds(4);
            }

            System.out.println("Press enter to read final commited value...");
            try (Connection readConnection = dbClient.getDataSource().getConnection())
            {
                readConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                readConnection.setAutoCommit(false);

                Customer customer = customerService.getCustomerById(customerId);
                System.out.println("Final value: " + customer.customerCredits() + "\n");
                sleepSeconds(4);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
