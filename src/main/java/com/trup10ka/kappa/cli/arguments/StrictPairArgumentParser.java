package com.trup10ka.kappa.cli.arguments;

import com.trup10ka.kappa.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A command argument parser that strictly handles key-value pairs.
 * <p>
 * This parser expects input arguments in the form of key-value pairs where the keys must be valid
 * as per the constructor parameters. It also ensures that each key is associated with a non-empty value.
 * The arguments are parsed by splitting them into pairs based on whitespace, with each pair being separated
 * by the specified key-value delimiter.
 * </p>
 *
 * <p>
 * The {@code parse} method parses the arguments and returns a map of valid key-value pairs.
 * Invalid pairs, such as missing values or unrecognized keys, are reported to the console.
 * </p>
 */
public class StrictPairArgumentParser implements CommandArgumentParser
{

    private final String[] keys;

    /**
     * Constructs a {@code StrictPairArgumentParser} with the given valid keys.
     *
     * @param keys An array of valid keys that can be used in the key-value pairs.
     */
    public StrictPairArgumentParser(String[] keys)
    {
        this.keys = keys;
    }

    /**
     * Parses the input string containing key-value pairs.
     * <p>
     * This method expects the input string to consist of key-value pairs separated by a specified delimiter.
     * It validates each pair by checking if the key is in the list of valid keys, and if the value is non-empty.
     * Invalid pairs are logged to the console and ignored.
     * </p>
     *
     * @param args The string containing the arguments to be parsed.
     * @param keyValueDelimiter The delimiter used to separate keys and values in each pair.
     * @return A map containing the valid key-value pairs found in the input string.
     */
    @Override
    @NotNull
    public Map<String, @Nullable String> parse(@NotNull String args, @NotNull String keyValueDelimiter)
    {
        Map<String, @Nullable String> result = new HashMap<>();
        List<String> pairs = StringUtil.splitOnEverySecondWhiteSpace(args);
        List<String> validKeys = Arrays.asList(keys);

        for (String pair : pairs) {
            String[] keyValue = pair.split(keyValueDelimiter);
            if (keyValue.length != 2 || keyValue[1].isEmpty()) {
                System.out.println("Missing value for key: " + keyValue[0]);
                continue;
            }
            if (!validKeys.contains(keyValue[0])) {
                System.out.println("Unrecognized key: " + keyValue[0]);
                continue;
            }
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }

    /**
     * Parses the input string containing key-value pairs, using a default delimiter of a space character.
     *
     * @param args The string containing the arguments to be parsed.
     * @return A map containing the valid key-value pairs found in the input string.
     */
    @Override
    public @NotNull Map<String, @Nullable String> parse(@NotNull String args)
    {
        return parse(args, " ");
    }
}
