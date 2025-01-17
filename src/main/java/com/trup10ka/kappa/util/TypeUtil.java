package com.trup10ka.kappa.util;

public class TypeUtil
{
    public static int tryParseIntField(String number, String fieldName)
    {
        try
        {
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e)
        {
            System.out.println("You provided incorrect format for " + fieldName + ". Correct format is integer");
            return -1;
        }
    }

    public static float tryParseFloatField(String number, String fieldName)
    {
        try
        {
            return Float.parseFloat(number);
        }
        catch (NumberFormatException e)
        {
            System.out.println("You provided incorrect format for " + fieldName + ". Correct format is double");
            return -1;
        }
    }
}
