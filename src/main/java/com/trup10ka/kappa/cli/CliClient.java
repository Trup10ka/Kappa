package com.trup10ka.kappa.cli;

import com.trup10ka.kappa.cli.commands.*;
import com.trup10ka.kappa.cli.commands.customer.DeleteCustomerCommand;
import com.trup10ka.kappa.cli.commands.customer.ImportCustomersCommand;
import com.trup10ka.kappa.cli.commands.customer.InsertCustomerCommand;
import com.trup10ka.kappa.cli.commands.customer.ShowAllCustomersCommand;
import com.trup10ka.kappa.cli.commands.order.ShowAllOrders;
import com.trup10ka.kappa.cli.commands.order.DeleteOrderCommand;
import com.trup10ka.kappa.cli.commands.order.InsertOrderCommand;
import com.trup10ka.kappa.cli.commands.product.ExportMostOrderedProductCommand;
import com.trup10ka.kappa.cli.commands.util.ExitCommand;
import com.trup10ka.kappa.cli.commands.util.HelpCommand;
import com.trup10ka.kappa.cli.commands.util.SimulateDirtyReadCommand;
import com.trup10ka.kappa.cli.commands.util.SimulateDirtyWritesCommand;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.services.ServiceManager;
import com.trup10ka.kappa.file.exp.CSVFileExportHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Map;
import java.util.Scanner;

import static com.trup10ka.kappa.cli.commands.CommandIdentifier.*;
import static java.util.Map.entry;


public class CliClient
{
    @NotNull
    private Map<String, Command> commands;

    @NotNull
    private final Scanner scanner = new Scanner(System.in);

    @NotNull
    private final DbClient dbClient;

    public CliClient(@NotNull DbClient dbClient)
    {
        this.dbClient = dbClient;

        initCommands();
    }

    public void start()
    {
        System.out.println("Welcome to Kappa CLI");
        System.out.println("Type 'help' to see available commands");
        System.out.println("Type 'help <command>' to see help for a specific command");
        while (true)
        {
            System.out.print("kappa> ");
            String input = scanner.nextLine();

            if (input.isBlank())
            {
                continue;
            }

            tryMatchCommandAndExecute(input);
        }
    }

    private void tryMatchCommandAndExecute(@NotNull String input)
    {
        CommandIdentifier commandIdentifier = parseCommand(input);
        String args = input.split(" ", 2).length > 1 ? input.split(" ", 2)[1] : null;

        if (commandIdentifier == null)
        {
            System.out.println("Unknown command");
            return;
        }

        executeCommand(commandIdentifier, args);
    }

    private void executeCommand(@NotNull CommandIdentifier commandIdentifier, String args)
    {
        Command command = commands.get(commandIdentifier.identifier);

        if (command == null)
        {
            System.out.println("Command not implemented");
            return;
        }

        String result = command.execute(args);

        if (!result.isEmpty())
        {
            System.out.println(result);
        }
    }

    @Nullable
    private CommandIdentifier parseCommand(@NotNull String input)
    {
        String[] inputParts = input.split(" ");

        return CommandIdentifier.fromString(inputParts[0]);
    }

    @NotNull
    public DbClient getDbClient()
    {
        return dbClient;
    }

    public void initCommands()
    {
        ServiceManager serviceManager = dbClient.getServiceManager();

        HelpCommand helpCommand = new HelpCommand(HELP);


        commands = Map.ofEntries(

                entry(
                        SHOW_ALL_CUSTOMERS_ORDERS.identifier,
                        new ShowAllOrders(SHOW_ALL_CUSTOMERS_ORDERS, serviceManager.getOrderService())
                ),

                entry(
                        SHOW_CUSTOMERS.identifier,
                        new ShowAllCustomersCommand(SHOW_CUSTOMERS, serviceManager.getCustomerService())
                ),

                entry(
                        INSERT_CUSTOMER.identifier,
                        new InsertCustomerCommand(INSERT_CUSTOMER, serviceManager.getCustomerService())
                ),

                entry(
                        INSERT_ORDER.identifier,
                        new InsertOrderCommand(INSERT_ORDER, serviceManager.getOrderService())
                ),

                entry(
                        DELETE_CUSTOMER.identifier,
                        new DeleteCustomerCommand(DELETE_CUSTOMER, serviceManager.getCustomerService())
                ),

                entry(
                        DELETE_ORDER.identifier,
                        new DeleteOrderCommand(DELETE_ORDER, serviceManager.getOrderService())
                ),

                entry(
                        SIMULATE_DIRTY_READ.identifier,
                        new SimulateDirtyReadCommand(SIMULATE_DIRTY_READ, serviceManager.getCustomerService(), dbClient)
                ),

                entry(
                        SIMULATE_DIRTY_WRITE.identifier,
                        new SimulateDirtyWritesCommand(SIMULATE_DIRTY_WRITE, serviceManager.getCustomerService(), dbClient)
                ),

                entry(
                        EXPORT_MOST_ORDERED_PRODUCT.identifier,
                        new ExportMostOrderedProductCommand(
                                EXPORT_MOST_ORDERED_PRODUCT, serviceManager.getAggregatedDataService(), new CSVFileExportHandler("export/most_ordered_products.csv"))
                ),

                entry(
                        IMPORT_CUSTOMERS.identifier,
                        new ImportCustomersCommand(IMPORT_CUSTOMERS, serviceManager.getAggregatedDataService())
                ),

                entry(
                        HELP.identifier, helpCommand
                ),

                entry(
                        EXIT.identifier, new ExitCommand()
                )
        );

        helpCommand.initHelpCommand(commands.values().stream().toList());
    }
}
