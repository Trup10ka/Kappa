package com.trup10ka.kappa.cli.commands;

import com.trup10ka.kappa.cli.arguments.CommandArgumentParser;
import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import com.trup10ka.kappa.data.Order;
import com.trup10ka.kappa.db.services.OrderService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.util.Map;

import static com.trup10ka.kappa.util.DateUtil.parseDate;

public class InsertOrderCommand extends Command
{
    private final OrderService orderService;

    private final CommandArgumentParser argumentParser =
            new StrictPairArgumentParser( new String[] { "-cid", "-pd", "-p", "-da", "-dz", "-ed", "-n" } );

    public InsertOrderCommand(@NotNull CommandIdentifier identifier, OrderService orderService)
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


        Map<String, String> parsedArguments = argumentParser.parse(args);
        if (parsedArguments.isEmpty())
            return "Failed to parse arguments";


        Order order = parseOrder(parsedArguments);
        if (order == null)
            return "Failed to insert order";

        int orderId = orderService.addOrder(order);

        return orderId == -1 ? "Failed to insert order, DB error" : "Order inserted successfully, id of the newly inserted order: " + orderId;
    }

    private Order parseOrder(Map<String, String> parsedArguments)
    {
        int customerId = Integer.parseInt(parsedArguments.get("-cid"));
        String placeDate = parsedArguments.get("-pd");
        float price = Float.parseFloat(parsedArguments.get("-p"));
        String deliveryAddress = parsedArguments.get("-da");
        String deliveryZip = parsedArguments.get("-dz");
        String notes = parsedArguments.get("-n");

        Date expectedDelivery = parseDate(parsedArguments.get("-ed"));
        if (expectedDelivery == null)
            return null;

        return new Order(customerId, placeDate, price, deliveryAddress, deliveryZip, expectedDelivery, notes);
    }
}
