package com.trup10ka.kappa.data;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record FatOrder(
        @NotNull String customerFirstName,
        @NotNull String customerLastName,
        @NotNull Map<Product, Integer> products
)
{
    @Override
    public String toString()
    {
        return """
        Customer: %s %s
        Products: %s
        """.formatted(customerFirstName, customerLastName, products.entrySet().stream()
                .map(entry -> entry.getKey().productName() + ": " + entry.getValue())
                .toList());
    }
}
