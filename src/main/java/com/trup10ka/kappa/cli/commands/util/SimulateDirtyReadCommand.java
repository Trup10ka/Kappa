package com.trup10ka.kappa.cli.commands.util;

import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.CustomerSex;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.trup10ka.kappa.util.ThreadUtil.sleepSeconds;

/**
 * Command for simulating a dirty read scenario by creating a template customer and performing transactions
 * with the isolation level set to {@link Connection#TRANSACTION_READ_UNCOMMITTED} to demonstrate dirty reads.
 * <p>
 * This command demonstrates how dirty reads occur when one transaction reads uncommitted data from another transaction.
 * It performs the following steps:
 * <ul>
 *     <li>Creates a template user with initial credits.</li>
 *     <li>Starts a transaction to update the user credits, but does not commit the transaction.</li>
 *     <li>Starts a second transaction to read the uncommitted data (demonstrating a dirty read).</li>
 *     <li>Rolls back the first transaction and starts a third transaction to read the committed data.</li>
 * </ul>
 * </p>
 */
public class SimulateDirtyReadCommand extends IsolationSecurityTestCommand
{
    public SimulateDirtyReadCommand(@NotNull CommandIdentifier identifier, CustomerService customerService, DbClient dbClient)
    {
        super(identifier, customerService, dbClient);
        setShortDescription("Simulate dirty read command");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        System.out.println("By calling this command, a template user will be created to demonstrate dirty reads\n");
        sleepSeconds(4);
        System.out.println(
                "When calling this command, the isolation level is set to READ_UNCOMMITED to demonstrate dirty reads and CANNOT BE CHANGED, otherwise dirty read cannot be simulated \n"
        );
        sleepSeconds(4);

        System.out.println("Creating a template user with 100 credits\n");

        Customer customer = new Customer("Template", "Customer", CustomerSex.OTHER, 100);
        int customerId = customerService.addCustomer(customer);

        System.out.println("Template user created with id: " + customerId + "\n");

        sleepSeconds(4);
        System.out.println("Starting first transaction to write to the user credits... \n");

        startDirtyRead(customerId);

        askToRemoveTemplateCustomer(customerId);

        return "Dirty read simulation completed";
    }

    @Override
    public String getHelp()
    {
        return """
                ===================================================================================================================================================
                    sdr - Simulate dirty read command
                
                    Usage: sdr
                ===================================================================================================================================================
                """;
    }

    private void startDirtyRead(int customerId)
    {
        try (Connection writeConnection = dbClient.getDataSource().getConnection())
        {
            writeConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            writeConnection.setAutoCommit(false);

            System.out.println("Updating user credits to 150.. \n");
            Statement writeStatement = writeConnection.createStatement();
            writeStatement.executeUpdate("UPDATE customer SET customer_credits = 150 WHERE id = " + customerId);

            System.out.println("Transaction 1 completed, NOT COMMITED\n");
            sleepSeconds(4);

            System.out.println("Press enter to continue...");
            scanner.nextLine();

            System.out.println("Starting transaction 2 to read the user credits...\n");

            try (Connection readConnection = dbClient.getDataSource().getConnection())
            {
                readConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                readConnection.setAutoCommit(false);

                Statement readStatement = readConnection.createStatement();
                Customer readCustomer = getCustomerFromResultSet(
                        readStatement.executeQuery("SELECT * FROM customer WHERE id = " + customerId)
                );

                System.out.println("Transaction 2 completed, read user credits: " + readCustomer.customerCredits());
                sleepSeconds(4);

                System.out.println("Rolling back transaction 1...\n");
                writeConnection.rollback();
                writeConnection.close();
                System.out.println("Transaction 1 rolled back and connection closed\n");
                sleepSeconds(4);

                System.out.println("Starting transaction 3 to read the user credits...\n");

                try (Connection readConnectionCommited = dbClient.getDataSource().getConnection())
                {
                    readConnectionCommited.setAutoCommit(false);

                    Customer readCustomerCommited = customerService.getCustomerById(customerId);

                    System.out.println("Transaction 3 completed, read user credits: " + readCustomerCommited.customerCredits());
                    sleepSeconds(4);
                }
                catch (Exception e)
                {
                    System.out.println("Transaction 3 failed");
                }
            }
            catch (Exception e)
            {
                System.out.println("Transaction 2 failed");
            }
        }
        catch (Exception e)
        {
            System.out.println("Transaction 1 failed");
        }
    }

    private Customer getCustomerFromResultSet(ResultSet resultSet) throws Exception
    {
        if (resultSet.next())
        {
            return new Customer(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    CustomerSex.valueOf(resultSet.getString("sex")),
                    resultSet.getInt("customer_credits")
            );
        }
        return null;
    }
}
