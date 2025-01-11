package com.trup10ka.kappa.exceptions;

public class InvalidConfigException extends Exception
{
    public InvalidConfigException(String field)
    {
        super("Invalid configuration value for field: " + field);
    }

    public InvalidConfigException(String field, String message)
    {
        super("Invalid configuration value for field: " + field + ". " + message);
    }
}
