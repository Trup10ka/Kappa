package com.trup10ka.kappa.db.services;


import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.export.MostOrderedProduct;
import com.trup10ka.kappa.db.DbClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AggregatedDataHikariService extends DatabaseService implements AggregatedDataService
{

    public AggregatedDataHikariService(DbClient dbClient)
    {
        super(dbClient);
    }

    @Override
    @Nullable
    public List<MostOrderedProduct> exportMostOrderedItems()
    {
        try (Connection connection = dbClient.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM most_ordered_products")
        )
        {
            ResultSet result = preparedStatement.executeQuery();
            return getMostOrderedProducts(result);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int importCustomers(List<Customer> customers)
    {
        try (Connection connection = dbClient.getDataSource().getConnection())
        {
            String insertBatch = "INSERT INTO customer (first_name, last_name, sex, customer_credits) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertBatch);

            for (Customer customer : customers)
            {
                preparedStatement.setString(1, customer.firstName());
                preparedStatement.setString(2, customer.lastName());
                preparedStatement.setString(3, String.valueOf(customer.sex()));
                preparedStatement.setInt(4, customer.customerCredits());
                preparedStatement.addBatch();
            }
            return preparedStatement.executeBatch().length;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public int importProductsCategories(List<String> productsCategories)
    {
        try (Connection connection = dbClient.getDataSource().getConnection())
        {
            String insertBatch = "INSERT INTO product_category (category_name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertBatch);

            for (String category : productsCategories)
            {
                preparedStatement.setString(1, category);
                preparedStatement.addBatch();
            }
            return preparedStatement.executeBatch().length;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @NotNull
    private List<MostOrderedProduct> getMostOrderedProducts(ResultSet result) throws SQLException
    {
        List<MostOrderedProduct> mostOrderedProducts = new ArrayList<>();
        while (result.next())
        {
            mostOrderedProducts.add(new MostOrderedProduct(
                    result.getString("product_name"),
                    result.getInt("total_ordered")
            ));
        }
        return mostOrderedProducts;
    }
}
