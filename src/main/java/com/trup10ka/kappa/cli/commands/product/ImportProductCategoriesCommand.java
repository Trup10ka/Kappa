package com.trup10ka.kappa.cli.commands.product;

import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.cli.commands.util.ImportCommand;
import com.trup10ka.kappa.data.util.Tuple;
import com.trup10ka.kappa.db.services.AggregatedDataService;
import com.trup10ka.kappa.file.imp.ImportHandler;
import com.trup10ka.kappa.util.FileFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImportProductCategoriesCommand extends ImportCommand
{

    private final AggregatedDataService aggregatedDataService;

    public ImportProductCategoriesCommand(@NotNull CommandIdentifier identifier, @NotNull AggregatedDataService aggregatedDataService)
    {
        super(identifier);
        this.aggregatedDataService = aggregatedDataService;
        setShortDescription("Import product categories from a file");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        Tuple<String, FileFormat> pathAndFormat = parseArguments(args);

        if (pathAndFormat == null)
            return "No product categories imported";

        ImportHandler importHandler = getImportHandlerByFormat(pathAndFormat.second());
        List<String> importedProductsCategories = importHandler.importProducts(pathAndFormat.first());

        if (importedProductsCategories.isEmpty())
            return "No product categories imported";

        int result = aggregatedDataService.importProductsCategories(importedProductsCategories);

        return "Imported " + result + " product categories";
    }

    @Override
    public String getHelp()
    {
        return "";
    }
}
