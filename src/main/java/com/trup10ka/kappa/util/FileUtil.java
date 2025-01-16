package com.trup10ka.kappa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileUtil
{
    public static void copyFileIfNotExists(String filePath, String defaultFileName)
    {
        if (Files.exists(Paths.get(filePath)))
            return;

        System.err.println("Config file not found, generating template config");

        copyResourceToFile(filePath, defaultFileName);

        System.out.println("Template config generated, please fill in the values in config.conf, program will now exit");

        System.exit(0);
    }

    public static boolean createParentDirectoriesIfNotExists(File parentDirectory)
    {
        if (!parentDirectory.exists())
        {
            return parentDirectory.getParentFile().mkdirs();
        }
        return false;
    }

    private static void copyResourceToFile(String filePath, String defaultFileName)
    {
        try (InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(defaultFileName))
        {
            if (inputStream == null)
            {
                throw new FileNotFoundException("Default config file not found in resources");
            }
            Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
        {
            System.err.println("Failed to generate template config, program is exiting, try running again");
            System.exit(1);
        }
    }
}
