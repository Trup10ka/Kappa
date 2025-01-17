package com.trup10ka.kappa.data;

import org.jetbrains.annotations.NotNull;

public record FatCustomer(
    int customerId,
    @NotNull String firstName,
    @NotNull String lastName,
    @NotNull CustomerSex sex,
    int customerCredits
)
{
    @Override
    public String toString()
    {
        return """
                --- %s %s ---
                ID: %d
                Sex: %s
                Credits: %d
                -------------
                """.formatted(firstName, lastName, customerId, sex, customerCredits);
    }
}
