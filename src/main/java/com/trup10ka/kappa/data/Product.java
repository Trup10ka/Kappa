package com.trup10ka.kappa.data;

import org.jetbrains.annotations.NotNull;

public record Product(
        @NotNull String productCategory,
        @NotNull String productName,
        @NotNull String productDescription,
        boolean isProductAvailable,
        double productPrice
)
{
    public Product(String productName)
    {
        this("Unknown", productName, "Unknown", false, 0.0);
    }
}
