package com.trup10ka.kappa.cli;

import com.trup10ka.kappa.cli.commands.*;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.services.ServiceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Scanner;

import static com.trup10ka.kappa.cli.commands.CommandIdentifier.INSERT_CUSTOMER;
import static com.trup10ka.kappa.cli.commands.CommandIdentifier.DELETE_CUSTOMER;
import static com.trup10ka.kappa.cli.commands.CommandIdentifier.EXIT;


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

        commands = Map.of(
                INSERT_CUSTOMER.identifier, new InsertCustomerCommand(INSERT_CUSTOMER, serviceManager.getCustomerService()),
                DELETE_CUSTOMER.identifier, new DeleteTransactionCommand(DELETE_CUSTOMER),
                EXIT.identifier, new ExitCommand()
        );
    }
}
