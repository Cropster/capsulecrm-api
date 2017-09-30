package com.cropster.capsulecrm.client;

/**
 * @author Casey Link
 * created on 2017-09-19
 */
public class Utils
{
    public static void validateNonNull(Object o)
    {
        if (o == null)
        {
            throw new NullPointerException();
        }
    }

    public static void validateNonNull(Object o, String message)
    {
        if (o == null)
        {
            throw new NullPointerException(message);
        }
    }
}
