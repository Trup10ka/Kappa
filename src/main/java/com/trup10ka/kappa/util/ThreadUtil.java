package com.trup10ka.kappa.util;

public class ThreadUtil
{
    public static void sleepMillis(long millis)
    {
        try
        {
            Thread.sleep(millis);
        } catch (InterruptedException ignored)
        {
        }
    }

    public static void sleepSeconds(long seconds)
    {
        sleepMillis(seconds * 1000);
    }
}
