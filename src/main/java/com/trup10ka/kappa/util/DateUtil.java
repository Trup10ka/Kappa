package com.trup10ka.kappa.util;

import java.sql.Date;

public class DateUtil
{
    public static Date parseDate(String date)
    {
        try
        {
            return Date.valueOf(date);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("You provided incorrect format for date. Correct format is yyyy-mm-dd");
            return null;
        }
    }
}
