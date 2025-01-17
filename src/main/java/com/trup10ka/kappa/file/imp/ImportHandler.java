package com.trup10ka.kappa.file.imp;


import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.Product;

import java.util.List;

public interface ImportHandler
{
    String IMPORT_FOLDER = "import/";

    List<Customer> importCustomers(String filePath);

    List<Product> importProducts(String filePath);
}
