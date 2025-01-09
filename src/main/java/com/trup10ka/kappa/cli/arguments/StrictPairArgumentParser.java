package com.trup10ka.kappa.cli.arguments;

import com.trup10ka.kappa.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrictPairArgumentParser implements CommandArgumentParser
{
    @Override
    @NotNull
    public Map<String, @Nullable String> parse(@NotNull String args, @NotNull String keyValueDelimiter)
    {
        Map<String, @Nullable String> result = new HashMap<>();

        List<String> pairs = StringUtil.splitOnEverySecondWhiteSpace(args);

        for (String pair : pairs)
        {
            String[] keyValue = pair.split(keyValueDelimiter);
            if (keyValue.length != 2)
            {
                throw new IllegalArgumentException("Missing value for key: " + keyValue[0]);
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
