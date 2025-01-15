package com.trup10ka.kappa.cli.commands;

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
}
