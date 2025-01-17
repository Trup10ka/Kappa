package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.db.DbClient;

import java.util.ArrayList;
import java.util.List;

public class ProductHikariService extends DatabaseService implements ProductService
{
    public ProductHikariService(DbClient dbClient)
    {
        super(dbClient);
    }

    @Override
    public List<String> getAllProductCategories()
    {
        try (var connection = dbClient.getDataSource().getConnection())
        {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT DISTINCT category_name FROM product_category");

            var categories = new ArrayList<String>();
            while (resultSet.next())
            {
                categories.add(resultSet.getString("category_name"));
            }

            return categories;
        }
        catch (java.sql.SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return List.of();
    }
}
