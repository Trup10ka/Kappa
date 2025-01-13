package com.trup10ka.kappa.cli.arguments;

import com.trup10ka.kappa.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrictPairArgumentParser implements CommandArgumentParser
{

    private final String[] keys;

    public StrictPairArgumentParser(String[] keys)
    {
        this.keys = keys;
    }

    @Override
    @NotNull
    public Map<String, @Nullable String> parse(@NotNull String args, @NotNull String keyValueDelimiter)
    {
        Map<String, @Nullable String> result = new HashMap<>();
        List<String> pairs = StringUtil.splitOnEverySecondWhiteSpace(args);
        List<String> validKeys = Arrays.asList(keys);

        for (String pair : pairs)
        {
            String[] keyValue = pair.split(keyValueDelimiter);
            if (keyValue.length != 2 || keyValue[1].isEmpty())
            {
                System.out.println("Missing value for key: " + keyValue[0]);
                return new HashMap<>();
            }
            if (!validKeys.contains(keyValue[0]))
            {
                System.out.println("Unrecognized key: " + keyValue[0]);
                return new HashMap<>();
            }
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }

    @Override
    public @NotNull Map<String, @Nullable String> parse(@NotNull String args)
    {
        return parse(args, " ");
    }

}
