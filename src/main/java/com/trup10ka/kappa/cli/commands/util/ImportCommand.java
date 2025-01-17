package com.trup10ka.kappa.cli.commands.util;

import com.trup10ka.kappa.cli.arguments.CommandArgumentParser;
import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import com.trup10ka.kappa.cli.commands.Command;
import com.trup10ka.kappa.cli.commands.CommandIdentifier;
import com.trup10ka.kappa.data.util.Tuple;
import com.trup10ka.kappa.file.imp.CSVFileImportHandler;
import com.trup10ka.kappa.file.imp.ImportHandler;
import com.trup10ka.kappa.util.FileFormat;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public abstract class ImportCommand extends Command
{
    private final CommandArgumentParser argumentParser = new StrictPairArgumentParser(new String[] { "-p", "-f" } );

    public ImportCommand(@NotNull CommandIdentifier identifier)
    {
        super(identifier);
    }

    protected Tuple<String, FileFormat> parseArguments(String args)
    {
        if (args == null || args.isEmpty())
        {
            System.out.println("Empty arguments. Please provide a file path and a format.");
            return null;
        }

        Map<String, String> parsedArguments = argumentParser.parse(args);
        if (!areMandatoryArgumentsPresent(parsedArguments))
        {
            System.out.println("Missing mandatory arguments, cannot import");
            return null;
        }

        String filePath = parsedArguments.get("-p");
        FileFormat format = parseFormat(parsedArguments.get("-f"));

        if (format == null)
        {
            System.out.println("Invalid format, cannot import");
            return null;
        }

        return Tuple.of(filePath, format);
    }

    protected ImportHandler getImportHandlerByFormat(FileFormat format)
    {
        return switch (format)
        {
            case CSV -> new CSVFileImportHandler();
        };
    }

    protected FileFormat parseFormat(String format)
    {
        try
        {
            return FileFormat.fromString(format);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Invalid format: " + format);
            return null;
        }
    }

    protected boolean areMandatoryArgumentsPresent(Map<String, String> parsedArguments)
    {
        if (!parsedArguments.containsKey("-p"))
        {
            System.out.println("Missing path argument");
            return false;
        }

        if (!parsedArguments.containsKey("-f"))
        {
            System.out.println("Missing format argument");
            return false;
        }
        return true;
    }
}
