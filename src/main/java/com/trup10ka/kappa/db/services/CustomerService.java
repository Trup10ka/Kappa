package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.CustomerSex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CustomerService
{
    int addCustomer(
            @NotNull String firstName,
            @NotNull String lastName,
            @NotNull CustomerSex email,
            int customerCredits
    );

    boolean deleteCustomerByName(@NotNull String firstName, @NotNull String lastName);

    boolean deleteCustomerById(int customerId);

    boolean updateCustomer(int customerId, Customer customer);

    @Nullable
    Customer getCustomerById(int customerId);

    @Nullable
    Customer getCustomerByName(@NotNull String firstName, @NotNull String lastName);
}