package com.trup10ka.kappa.cli;

import com.trup10ka.kappa.cli.commands.*;
import com.trup10ka.kappa.db.DbClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Scanner;

import static com.trup10ka.kappa.cli.commands.CommandIdentifier.CREATE_TRANSACTION;
import static com.trup10ka.kappa.cli.commands.CommandIdentifier.DELETE_TRANSACTION;
import static com.trup10ka.kappa.cli.commands.CommandIdentifier.EXIT;


public class CliClient
{
    @NotNull
    private final Map<String, Command> commands = Map.of(
            CREATE_TRANSACTION.identifier, new InsertCustomerCommand(CREATE_TRANSACTION, this),
            DELETE_TRANSACTION.identifier, new DeleteTransactionCommand(DELETE_TRANSACTION),
            EXIT.identifier, new ExitCommand()
    );

    @NotNull
    private final Scanner scanner = new Scanner(System.in);

    @NotNull
    private final DbClient dbClient;

    public CliClient(@NotNull DbClient dbClient)
    {
        this.dbClient = dbClient;
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

        if (commandIdentifier == null)
        {
            System.out.println("Unknown command");
            return;
        }

        executeCommand(commandIdentifier);
    }

    private void executeCommand(@NotNull CommandIdentifier commandIdentifier)
    {
        Command command = commands.get(commandIdentifier.identifier);

        if (command == null)
        {
            System.out.println("Command not implemented");
            return;
        }

        String result = command.execute();

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
}
