package com.trup10ka.kappa.cli.commands.product;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.db.services.ProductService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShowProductCategoriesCommand extends Command
{

    private final ProductService productService;

    public ShowProductCategoriesCommand(@NotNull CommandIdentifier identifier, @NotNull ProductService productService)
    {
        super(identifier);
        this.productService = productService;
        setShortDescription("Show all product categories");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        if (args != null)
        {
            System.out.println("This command does not take any arguments");
            return "";
        }

        var categories = productService.getAllProductCategories();

        if (categories.isEmpty())
        {
            return "No product categories found";
        }

        return categories.stream()
                .reduce("", (acc, category) -> acc + category + "\n");
    }

    @Override
    public String getHelp()
    {
        return """
                ========================================================================================================
                spc - Show all product categories
                
                Usage:
                    spc
                ========================================================================================================
                """;
    }
}
