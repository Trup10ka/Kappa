package com.trup10ka.kappa.config;


import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.NoFormatFoundException;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileConfigLoader implements ConfigLoader
{

    private final String filePath;

    public FileConfigLoader(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    @NotNull
    public KappaConfig loadConfig()
    {
        try (FileConfig fileConfig = FileConfig.of(filePath))
        {
            fileConfig.load();

            return new KappaConfig(
                    fileConfig.get("db-name"),
                    fileConfig.get("db-user"),
                    fileConfig.get("db-password"),
                    fileConfig.get("db-host")
            );
        }
        catch (NoFormatFoundException e)
        {
            System.err.println("No file with supported format found, generating template config, restart application");
            generateTemplateConfig();
            System.exit(1);
            return null;
        }
    }

    private void generateTemplateConfig()
    {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultConfig.conf"))
        {
            if (inputStream == null)
            {
                throw new FileNotFoundException("Default config file not found in resources");
            }
            Files.copy(inputStream, Paths.get("config.conf"), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
        {
            System.err.println("Failed to generate template config, program is exiting, try running again");
            System.exit(1);
        }
    }
}
