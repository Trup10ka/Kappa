package com.trup10ka.kappa.util;

public enum IsolationSecurity
{
    READ_UNCOMMITTED("READ_UNCOMMITTED"),
    READ_COMMITTED("READ_COMMITTED"),
    REPEATABLE_READ("REPEATABLE_READ"),
    SERIALIZABLE("SERIALIZABLE");

    /**
     * Returns the String representation of that isolation enum value.
     */
    public final String value;

    IsolationSecurity(String value)
    {
        this.value = value;
    }
}
