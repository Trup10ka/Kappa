package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.Order;
import com.trup10ka.kappa.db.DbClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Order> getAllOrders()
    {
        try (Connection connection = dbClient.getDataSource().getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM `order`");
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

    private List<Order> parseOrders(ResultSet result) throws SQLException
    {
        List<Order> orders = new ArrayList<>();
        while (result.next())
        {
            orders.add(new Order(
                    result.getInt("customer_id"),
                    result.getString("place_date"),
                    result.getFloat("price"),
                    result.getString("delivery_address"),
                    result.getString("delivery_zip"),
                    result.getDate("expected_delivery"),
                    result.getString("delivery_note")
            ));
        }
        return orders;
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
