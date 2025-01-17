package com.trup10ka.kappa.cli.commands.order;

import com.trup10ka.kappa.cli.arguments.CommandArgumentParser;
import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
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
        setShortDescription("Creates a new order");
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

    @Override
    public String getHelp()
    {
        return """
                ===================================================================================================================================================
                    co - Create order command
                
                    Usage: co -cid <customer_id> -pd <place_date> -p <price> -da <delivery_address> -dz <delivery_zip> -ed <expected_delivery> -n <notes>(optional)
                
                    Options:
                        -cid <customer_id>          Customer id
                        -pd <place_date>            Place date
                        -p <price>                  Price
                        -da <delivery_address>      Delivery address
                        -dz <delivery_zip>          Delivery zip
                        -ed <expected_delivery>     Expected delivery date
                        -n <notes>                  Notes (optional)
                ===================================================================================================================================================
                """;
    }

    private Order parseOrder(Map<String, String> parsedArguments)
    {
        int customerId = tryParseInt(parsedArguments.get("-cid"));
        String placeDate = parsedArguments.get("-pd");
        float price = Float.parseFloat(parsedArguments.get("-p"));
        String deliveryAddress = parsedArguments.get("-da");
        String deliveryZip = parsedArguments.get("-dz");
        String notes = parsedArguments.get("-n");

        Date expectedDelivery = parseDate(parsedArguments.get("-ed"));
        if (expectedDelivery == null || customerId == -1)
            return null;

        return new Order(customerId, placeDate, price, deliveryAddress, deliveryZip, expectedDelivery, notes);
    }

    private int tryParseInt(String value)
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Customer ID is not of type int");
            return -1;
        }
    }
}
