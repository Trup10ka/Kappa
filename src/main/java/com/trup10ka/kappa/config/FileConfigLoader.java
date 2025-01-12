package com.trup10ka.kappa.config;


import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.NoFormatFoundException;
import com.trup10ka.kappa.util.IsolationSecurity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static com.trup10ka.kappa.util.FileUtil.copyFileIfNotExists;

public class FileConfigLoader implements ConfigLoader
{
    private final String[] requiredFields = { "db-name", "db-user", "db-password", "db-host", "db-isolation-security" };

    private final String filePath;

    public FileConfigLoader(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    @NotNull
    public KappaConfig loadConfig()
    {
        copyFileIfNotExists("config.conf", "config.conf");
        try (FileConfig fileConfig = FileConfig.of(filePath))
        {
            fileConfig.load();
            checkIfAnyFiledInConfigIsMissing(fileConfig);

            IsolationSecurity isolationSecurity = parseIsolationSecurity(fileConfig);

            return new KappaConfig(
                    fileConfig.get("db-name"),
                    fileConfig.get("db-user"),
                    fileConfig.get("db-password"),
                    fileConfig.get("db-host"),
                    isolationSecurity
            );
        }
        catch (NoFormatFoundException e)
        {
            copyFileIfNotExists("config.conf", "config.conf");
            System.exit(1);
            return null;
        }
    }

    private IsolationSecurity parseIsolationSecurity(FileConfig fileConfig)
    {
        String isolationSecurityAsString = fileConfig.get("db-isolation-security");
        try
        {
            return IsolationSecurity.valueOf(isolationSecurityAsString);
        }
        catch (IllegalArgumentException ignored)
        {
            System.out.println("Invalid isolation security value: " + isolationSecurityAsString + ". Available candidates are: " + Arrays.toString(IsolationSecurity.values()));
            System.exit(1);
        }
        return null;
    }

    private void checkIfAnyFiledInConfigIsMissing(FileConfig fileConfig)
    {
        for (String requiredField : requiredFields)
        {
            if (!fileConfig.contains(requiredField))
            {
                System.out.println("Missing required field in the config file: " + requiredField);
                System.exit(1);
            }
        }
    }
}
