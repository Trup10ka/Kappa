package com.trup10ka.kappa.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
    @NotNull
    public static List<@NotNull String> splitOnEverySecondWhiteSpace(@NotNull String args)
    {
        String regex = "(\\S+\\s+\\S+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(args);

        List<String> result = new ArrayList<>();

        while (matcher.find())
        {
            result.add(matcher.group());
        }

        return result;
    }
}
