package com.trup10ka.kappa.cli.commands.customer;

import com.trup10ka.kappa.cli.arguments.CommandArgumentParser;
import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.db.services.AggregatedDataService;
import com.trup10ka.kappa.file.imp.CSVFileImportHandler;
import com.trup10ka.kappa.file.imp.ImportHandler;
import com.trup10ka.kappa.file.imp.JSONFileImportHandler;
import com.trup10ka.kappa.util.FileFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ImportCustomersCommand extends Command
{

    private final AggregatedDataService aggregatedDataService;

    private final CommandArgumentParser argumentParser = new StrictPairArgumentParser(new String[] { "-p", "-f" } );

    public ImportCustomersCommand(@NotNull CommandIdentifier identifier, @NotNull AggregatedDataService aggregatedDataService)
    {
        super(identifier);
        this.aggregatedDataService = aggregatedDataService;
        setShortDescription("Import customers from a file of a given format");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        if (args == null || args.isEmpty())
            return "Empty arguments. Please provide a file path and a format.";


        Map<String, String> parsedArguments = argumentParser.parse(args);
        if (!areMandatoryArgumentsPresent(parsedArguments))
            return "Missing mandatory arguments, cannot import";


        String filePath = parsedArguments.get("-p");
        FileFormat format = parseFormat(parsedArguments.get("-f"));

        if (format == null)
            return "\n";

        ImportHandler importHandler = getImportHandlerByFormat(format);
        List<Customer> importedCustomers = importHandler.importCustomers(filePath);

        if (importedCustomers.isEmpty())
            return "No customers imported";


        aggregatedDataService.importCustomers(importedCustomers);
        return "Imported " + importedCustomers.size() + " customers";
    }

    @Override
    public String getHelp()
    {
        return """
                ========================================================================================================
                ic - Import customers from a file of a given format
                
                Usage:
                    ic -p <file_path> -f <format>
                
                Options:
                    -p      Path to the file containing the customers
                    -f      Format of the file CSV or JSON)
                ========================================================================================================
                """;
    }

    private ImportHandler getImportHandlerByFormat(FileFormat format)
    {
        return switch (format)
        {
            case CSV -> new CSVFileImportHandler();
            case JSON -> new JSONFileImportHandler();
        };
    }

    private FileFormat parseFormat(String format)
    {
        try
        {
            return FileFormat.fromString(format);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Invalid format: " + format);
            return null;
        }
    }

    private boolean areMandatoryArgumentsPresent(Map<String, String> parsedArguments)
    {
        if (!parsedArguments.containsKey("-p"))
        {
            System.out.println("Missing path argument");
            return false;
        }

        if (!parsedArguments.containsKey("-f"))
        {
            System.out.println("Missing format argument");
            return false;
        }
        return true;
    }
}
