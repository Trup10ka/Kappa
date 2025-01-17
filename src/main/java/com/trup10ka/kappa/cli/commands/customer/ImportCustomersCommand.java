package com.trup10ka.kappa.cli.commands.customer;

import com.trup10ka.kappa.cli.arguments.CommandArgumentParser;
import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.cli.commands.util.ImportCommand;
import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.util.Tuple;
import com.trup10ka.kappa.db.services.AggregatedDataService;
import com.trup10ka.kappa.file.imp.ImportHandler;
import com.trup10ka.kappa.util.FileFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImportCustomersCommand extends ImportCommand
{

    private final AggregatedDataService aggregatedDataService;

    public ImportCustomersCommand(@NotNull CommandIdentifier identifier, @NotNull AggregatedDataService aggregatedDataService)
    {
        super(identifier);
        this.aggregatedDataService = aggregatedDataService;
        setShortDescription("Import customers from a file of a given format");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        Tuple<String, FileFormat> pathAndFormat = parseArguments(args);

        if (pathAndFormat == null)
            return "No customers imported";

        ImportHandler importHandler = getImportHandlerByFormat(pathAndFormat.second());
        List<Customer> importedCustomers = importHandler.importCustomers(pathAndFormat.first());

        if (importedCustomers.isEmpty())
            return "No customers imported";

        int result = aggregatedDataService.importCustomers(importedCustomers);
        return "Imported " + result + " customers";
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
}
