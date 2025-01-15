package com.trup10ka.kappa.util;

public enum IsolationLevel
{
    READ_UNCOMMITTED("TRANSACTION_READ_UNCOMMITTED"),
    READ_COMMITTED("TRANSACTION_READ_COMMITTED"),
    REPEATABLE_READ("TRANSACTION_REPEATABLE_READ"),
    SERIALIZABLE("TRANSACTION_SERIALIZABLE");

    /**
     * Returns the String representation of that isolation enum value.
     */
    public final String value;

    IsolationLevel(String value)
    {
        this.value = value;
    }
}
