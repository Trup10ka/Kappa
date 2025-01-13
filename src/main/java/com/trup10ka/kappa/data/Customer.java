package com.trup10ka.kappa.data;

import org.jetbrains.annotations.NotNull;

public record Customer(
    @NotNull String firstName,
    @NotNull String lastName,
    @NotNull CustomerSex sex,
    int customerCredits
)
{
}
