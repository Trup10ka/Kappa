package com.trup10ka.kappa.data.util;

public record Tuple<TValue, KValue>(TValue first, KValue second)
{
    public static <TValue, KValue> Tuple<TValue, KValue> of(TValue first, KValue second)
    {
        return new Tuple<>(first, second);
    }
}
