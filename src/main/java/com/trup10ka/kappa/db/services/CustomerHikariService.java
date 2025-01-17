package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.CustomerSex;
import com.trup10ka.kappa.data.FatCustomer;
import com.trup10ka.kappa.db.DbClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.List;

public class CustomerHikariService extends DatabaseService implements CustomerService
{

    public CustomerHikariService(DbClient dbClient)
    {
        super(dbClient);
    }

    @Override
    public int addCustomer(@NotNull Customer customer)
    {
        String insertCustomer = "INSERT INTO customer (first_name, last_name, sex, customer_credits) VALUES (?, ?, ?, ?)";

        try (var connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement statement = createStatementToInsertCustomer(connection, customer, insertCustomer);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            return returnCustomerIdFromResultSet(resultSet);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    @Override
    public boolean deleteCustomerByName(@NotNull String firstName, @NotNull String lastName)
    {
        String deleteCustomer = "DELETE FROM customer WHERE first_name = ? AND last_name = ?";

        try (var connection = dbClient.getDataSource().getConnection())
        {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(deleteCustomer);
            int affectedRows = statement.executeUpdate();

            connection.commit();
            return affectedRows > 0;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteCustomerById(int customerId)
    {
        String deleteCustomer = "DELETE FROM customer WHERE id = ?";

        try (var connection = dbClient.getDataSource().getConnection())
        {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(deleteCustomer);
            statement.setInt(1, customerId);
            int affectedRows = statement.executeUpdate();

            connection.commit();
            return affectedRows > 0;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateCustomer(int customerId, Customer customer)
    {
        String update = "UPDATE customer SET first_name = ?, last_name = ?, sex = ?, customer_credits = ? WHERE id = ?";

        try (var connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement statement = createUpdateStatement(connection, update, customer, customerId);
            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    @Nullable
    public Customer getCustomerById(int customerId)
    {
        String selectCustomer = "SELECT * FROM customer WHERE id = ?";

        try (var connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(selectCustomer);
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return parseCustomerFromResultSet(resultSet);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Customer getCustomerByName(@NotNull String firstName, @NotNull String lastName)
    {
        String selectCustomer = "SELECT * FROM customer WHERE first_name = ? AND last_name = ?";

        try (var connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(selectCustomer);
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return parseCustomerFromResultSet(resultSet);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<FatCustomer> getAllCustomers()
    {
        String selectAllCustomers = "SELECT * FROM customer";

        try (var connection = dbClient.getDataSource().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(selectAllCustomers);
            ResultSet resultSet = statement.executeQuery();

            return createCustomerListFromResultSet(resultSet);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    private List<FatCustomer> createCustomerListFromResultSet(ResultSet resultSet)
    {
        try (resultSet)
        {
            List<FatCustomer> customers = new java.util.ArrayList<>();
            while (resultSet.next())
            {
                customers.add(parseFatCustomerFromResultSet(resultSet));
            }
            return customers;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    private Customer parseCustomerFromResultSet(ResultSet resultSet) throws SQLException
    {
        return new Customer(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                CustomerSex.valueOf(resultSet.getString("sex")),
                resultSet.getInt("customer_credits"));
    }

    private FatCustomer parseFatCustomerFromResultSet(ResultSet resultSet) throws SQLException
    {
        return new FatCustomer(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                CustomerSex.valueOf(resultSet.getString("sex")),
                resultSet.getInt("customer_credits"));
    }

    private int returnCustomerIdFromResultSet(ResultSet resultSet)
    {
        try (resultSet)
        {
            if (resultSet.next())
                return resultSet.getInt(1);
            return -1;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    private PreparedStatement createUpdateStatement(Connection connection, String sql, Customer customer, int customerId) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, customer.firstName());
        statement.setString(2, customer.lastName());
        statement.setString(3, customer.sex().toString());
        statement.setInt(4, customer.customerCredits());
        statement.setInt(5, customerId);
        return statement;
    }

    private PreparedStatement createStatementToInsertCustomer(Connection connection, Customer customer, String sql) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, customer.firstName());
        statement.setString(2, customer.lastName());
        statement.setString(3, customer.sex().toString());
        statement.setInt(4, customer.customerCredits());

        return statement;
    }
}
