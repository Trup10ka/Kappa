package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.FatOrder;
import com.trup10ka.kappa.data.Order;
import com.trup10ka.kappa.data.Product;
import com.trup10ka.kappa.db.DbClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHikariService extends DatabaseService implements OrderService
{
    public OrderHikariService(DbClient dbClient)
    {
        super(dbClient);
    }

    @Override
    public int addOrder(Order order)
    {
        String sql = createOrderSQL(order);

        try (Connection connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement preparedStatement = prepareOrderInsertStatement(connection, order, sql);
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys())
            {
                if (resultSet.next())
                {
                    return resultSet.getInt(1);
                }
            }
        }
        catch (SQLException | IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean deleteOrderById(int orderId)
    {
        String sql = "DELETE FROM `order` WHERE id = ?";
        try (Connection connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            return preparedStatement.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateOrderById(int orderId)
    {
        System.out.println("Unfortunately, this method is not implemented yet.");
        return false;
    }

    @Override
    public Order getOrderById(int orderId)
    {
        return getOrder("SELECT * FROM `order` WHERE id = ?", orderId, false);
    }

    @Override
    public Order getOrderByCustomerId(int customerId)
    {
        return getOrder("SELECT * FROM `order` WHERE customer_id = ?", customerId, true);
    }

    private Order getOrder(String sql, int id, boolean isCustomerId)
    {
        try (Connection connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();
            return parseOrder(result);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<FatOrder> getAllOrders()
    {
        try (Connection connection = dbClient.getDataSource().getConnection())
        {
            String sql = "SELECT customer_first_name, customer_last_name, product_name, number_of_items FROM customer_products_in_order";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return parseOrders(result);
        }
        catch (SQLException | NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return List.of();
    }

    private PreparedStatement prepareOrderInsertStatement(Connection connection, Order order, String sql) throws SQLException, IllegalArgumentException
    {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, order.customerId());
        preparedStatement.setDate(2, Date.valueOf(order.placeDate()));
        preparedStatement.setDouble(3, order.price());
        preparedStatement.setString(4, order.deliveryAddress());
        preparedStatement.setString(5, order.deliveryZip());
        preparedStatement.setDate(6, Date.valueOf(String.valueOf(order.deliveryDate())));

        if (order.deliveryNote() != null)
        {
            preparedStatement.setString(7, order.deliveryNote());
        }

        return preparedStatement;
    }

    private List<FatOrder> parseOrders(ResultSet result) throws SQLException
    {
        Map<String, FatOrder> orderMap = new HashMap<>();

        while (result.next())
        {
            String customerFirstName = result.getString("customer_first_name");
            String customerLastName = result.getString("customer_last_name");
            String productName = result.getString("product_name");
            int numberOfItems = result.getInt("number_of_items");

            String customerKey = customerFirstName + " " + customerLastName;

            FatOrder order = orderMap.get(customerKey);
            if (order == null)
            {
                order = new FatOrder(customerFirstName, customerLastName, new HashMap<>());
                orderMap.put(customerKey, order);
            }

            order.products().put(new Product(productName), numberOfItems);
        }

        return new ArrayList<>(orderMap.values());
    }

    private Order parseOrder(ResultSet result) throws SQLException
    {
        if (result.next())
        {
            return new Order(
                    result.getInt("customer_id"),
                    result.getString("place_date"),
                    result.getFloat("price"),
                    result.getString("delivery_address"),
                    result.getString("delivery_zip"),
                    result.getDate("expected_delivery"),
                    result.getString("delivery_note")
            );
        }
        return null;
    }

    private String createOrderSQL(Order order)
    {
        if (order.deliveryNote() == null)
        {
            return "INSERT INTO `order` (customer_id, place_date, price, delivery_address, delivery_zip, expected_delivery) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        }
        return "INSERT INTO `order` (customer_id, place_date, price, delivery_address, delivery_zip, expected_delivery, delivery_note) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }
}
