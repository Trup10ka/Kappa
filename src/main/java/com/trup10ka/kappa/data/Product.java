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
}
