package com.trup10ka.kappa.cli.commands.order;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.data.FatOrder;
import com.trup10ka.kappa.db.services.OrderService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShowAllOrders extends Command
{

    private final OrderService orderService;

    public ShowAllOrders(@NotNull CommandIdentifier identifier, @NotNull OrderService orderService)
    {
        super(identifier);
        this.orderService = orderService;
        setShortDescription("See all items in a customer's order");
    }

    @Override
    public @NotNull String execute(@Nullable String args)
    {
        if (args != null)
        {
            System.out.println("This command does not take any arguments");
            return "";
        }

        List<FatOrder> orders = orderService.getAllOrders();

        return orders.stream()
                .map(FatOrder::toString)
                .reduce("", (acc, order) -> acc + order + "\n");
    }

    @Override
    public String getHelp()
    {
        return """
                ========================================================================================================
                sao - See all items in a customer's orders
                
                Usage:
                    sao
                ========================================================================================================
                """;
    }
}
