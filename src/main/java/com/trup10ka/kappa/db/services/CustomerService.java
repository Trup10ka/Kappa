package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.Customer;

public interface CustomerService
{
    int addCustomer();
    boolean deleteCustomer();
    boolean updateCustomer();
    Customer getCustomer();
}
