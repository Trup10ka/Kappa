package com.trup10ka.kappa.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public record Order(
    int customerId,
    @NotNull String placeDate,
    double price,
    @NotNull String deliveryAddress,
    @NotNull String deliveryZip,
    @NotNull LocalDateTime deliveryDate,
    @Nullable String deliveryNote
)
{
}
