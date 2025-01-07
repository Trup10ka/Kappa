package com.trup10ka.kappa.config;


import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.NoFormatFoundException;
import org.jetbrains.annotations.NotNull;

import static com.trup10ka.kappa.util.FileUtil.copyFileIfNotExists;

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
        copyFileIfNotExists("config.conf", "config.conf");
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
            copyFileIfNotExists("config.conf", "config.conf");
            System.exit(1);
            return null;
        }
    }
}
