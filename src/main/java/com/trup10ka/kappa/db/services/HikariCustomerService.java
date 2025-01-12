package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.db.DbClient;
import com.trup10ka.kappa.db.KappaTransactionManager;

public class HikariCustomerService implements CustomerService
{

    private final DbClient dbClient;

    private final KappaTransactionManager transactionManager;

    public HikariCustomerService(DbClient dbClient, KappaTransactionManager transactionManager)
    {
        this.dbClient = dbClient;
        this.transactionManager = transactionManager;
    }

    @Override
    public int addCustomer()
    {
        return 0;
    }

    @Override
    public boolean deleteCustomer()
    {
        return false;
    }

    @Override
    public boolean updateCustomer()
    {
        return false;
    }

    @Override
    public Customer getCustomer()
    {
        return null;
    }
}
