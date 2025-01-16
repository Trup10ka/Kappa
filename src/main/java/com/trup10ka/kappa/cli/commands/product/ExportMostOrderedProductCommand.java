package com.trup10ka.kappa.cli.commands.product;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.data.export.MostOrderedProduct;
import com.trup10ka.kappa.db.services.AggregatedDataService;
import com.trup10ka.kappa.file.exp.ExportHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExportMostOrderedProductCommand extends Command
{
    private final AggregatedDataService aggregatedDataService;

    private final ExportHandler exportHandler;

    public ExportMostOrderedProductCommand(@NotNull CommandIdentifier identifier, @NotNull AggregatedDataService aggregatedDataService, @NotNull ExportHandler exportHandler)
    {
        super(identifier);
        this.aggregatedDataService = aggregatedDataService;
        this.exportHandler = exportHandler;
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        List<MostOrderedProduct> products = aggregatedDataService.exportMostOrderedItems();

        if (products == null)
        {
            return "Failed to export most ordered products";
        }
        else if (products.isEmpty())
        {
            return "No products to export";
        }

        boolean success = exportHandler.exportMostOrderedItems(products);

        return success ? "Successfully exported most ordered products" : "Failed to export most ordered products";
    }

    @Override
    public String getHelp()
    {
        return "";
    }
}
