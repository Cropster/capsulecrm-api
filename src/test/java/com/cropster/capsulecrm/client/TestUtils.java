package com.cropster.capsulecrm.client;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TestUtils
{
    public static String fileToString(String fileName)
    {
        try
        {
            return FileUtils.readFileToString(new File("src/test/resources/" + fileName), "UTF-8");
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
