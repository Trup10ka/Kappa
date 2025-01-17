package com.trup10ka.kappa.util;

public enum FileFormat
{
    CSV("csv");

    public final String identifier;

    FileFormat(String identifier)
    {
        this.identifier = identifier;
    }

    public static FileFormat fromString(String format)
    {
        return switch (format.toLowerCase())
        {
            case "csv" -> CSV;
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        };
    }
}
