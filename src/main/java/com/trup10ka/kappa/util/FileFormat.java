package com.trup10ka.kappa.util;

public enum FileFormat
{
    JSON("json"),
    CSV("csv");

    public final String identifier;

    FileFormat(String identifier)
    {
        this.identifier = identifier;
    }

    public static FileFormat fromString(String format)
    {
        return switch (format)
        {
            case "json" -> JSON;
            case "csv" -> CSV;
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        };
    }
}
