package com.trup10ka.kappa.cli.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


/**
 * Interface representing a parser for command-line arguments.
 * Provides methods to parse a string of arguments into key-value pairs.
 */
public interface CommandArgumentParser
{
    /**
     * Parses the given string of arguments into a map of key-value pairs using the specified delimiter.
     *
     * @param args the string containing the arguments to parse; must not be null.
     *             Example format: "key1=value1 key2=value2".
     * @param keyValueDelimiter the delimiter that separates keys from values; must not be null.
     *                          Example: "=" for "key=value".
     * @return a map where keys are argument names and values are their associated values.
     *         If a key has no associated value, the value will be {@code null}.
     */
    @NotNull
    Map<String, @Nullable String> parse(@NotNull String args, @NotNull String keyValueDelimiter);

    /**
     * Parses the given string of arguments into a map of key-value pairs using a default delimiter.
     * The default delimiter is implementation-specific and may vary.
     *
     * @param args the string containing the arguments to parse; must not be null.
     *             Example format: "key1=value1 key2=value2".
     * @return a map where keys are argument names and values are their associated values.
     *         If a key has no associated value, the value will be {@code null}.
     */

    @NotNull
    Map<String, @Nullable String> parse(@NotNull String args);
}
