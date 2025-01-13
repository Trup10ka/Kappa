package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.db.DbClient;

public class ServiceManager
{
    private final DbClient dbClient;

    private final CustomerService customerService;

    private final OrderService orderService;

    private final ProductService productService;

    public ServiceManager(DbClient dbClient)
    {
        this.dbClient = dbClient;

        customerService = new CustomerHikariService(dbClient);
        orderService = new OrderHikariService(dbClient);
        productService = new ProductHikariService(dbClient);
    }

    public CustomerService getCustomerService()
    {
        return customerService;
    }

    public OrderService getOrderService()
    {
        return orderService;
    }

    public ProductService getProductService()
    {
        return productService;
    }
}
