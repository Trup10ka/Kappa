package com.trup10ka.kappa.file.imp;

import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.Product;

import java.util.List;

public interface ImportHandler
{
    int importCustomers(List<Customer> customers);

    int importProducts(List<Product> products);
}
