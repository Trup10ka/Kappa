package com.trup10ka.kappa.cli.commands.order;

import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.db.services.OrderService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DeleteOrderCommand extends Command
{

    private final OrderService orderService;

    public DeleteOrderCommand(CommandIdentifier identifier, OrderService orderService)
    {
        super(identifier);
        this.orderService = orderService;
        setShortDescription("Deletes an order by its ID");
    }

    @Override
    @NotNull
    public String execute(@Nullable String args)
    {
        if (args == null || args.isEmpty())
            return "No arguments provided";

        int orderId;
        try
        {
            orderId = Integer.parseInt(args);
        }
        catch (NumberFormatException e)
        {
            return "Invalid argument";
        }

        boolean success = orderService.deleteOrderById(orderId);

        return success ? "Order deleted successfully" : "Failed to delete order";
    }

    @Override
    public String getHelp()
    {
        return """
                ===================================================================================================================================================
                    do - Deletes an order by its ID.
                
                    Usage: do <order_id>
                ===================================================================================================================================================
                """;
    }
}
