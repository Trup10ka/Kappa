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


/**
 * Abstract class representing a command that handles importing data.
 * <p>
 * This class extends {@link Command} and provides functionality for parsing command-line arguments
 * related to import operations. The arguments include a file path and format, which are required for
 * processing the import. The class provides methods for validating the arguments, parsing the file format,
 * and retrieving the corresponding import handler based on the format.
 * </p>
 */
public abstract class ImportCommand extends Command
{

    private final CommandArgumentParser argumentParser = new StrictPairArgumentParser(new String[] { "-p", "-f" });

    /**
     * Constructs an {@code ImportCommand} with the given identifier.
     *
     * @param identifier The identifier for the command.
     */
    public ImportCommand(@NotNull CommandIdentifier identifier)
    {
        super(identifier);
    }

    /**
     * Parses the arguments provided for the import operation.
     * <p>
     * This method checks if the arguments are valid, ensures that mandatory arguments are present
     * (file path and format), and then returns a tuple containing the file path and the format.
     * </p>
     *
     * @param args The string containing the command-line arguments.
     * @return A tuple containing the file path and format if the arguments are valid,
     *         or {@code null} if there were issues with the arguments.
     */
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

    /**
     * Retrieves the appropriate import handler based on the specified file format.
     * <p>
     * This method determines the import handler to use for a given format. If the format is CSV,
     * the method returns an instance of {@link CSVFileImportHandler}.
     * </p>
     *
     * @param format The file format for which to retrieve the import handler.
     * @return The corresponding import handler for the given format.
     */
    protected ImportHandler getImportHandlerByFormat(FileFormat format)
    {
        return switch (format)
        {
            case CSV -> new CSVFileImportHandler();
        };
    }

    /**
     * Parses the file format from the provided string.
     * <p>
     * This method converts the given string into a {@link FileFormat} enum. If the string is invalid,
     * it prints an error message and returns {@code null}.
     * </p>
     *
     * @param format The string representing the file format.
     * @return The corresponding {@link FileFormat} enum, or {@code null} if the format is invalid.
     */
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

    /**
     * Checks if the mandatory arguments are present in the parsed arguments map.
     * <p>
     * This method verifies that both the file path (-p) and format (-f) arguments are present.
     * </p>
     *
     * @param parsedArguments The map of parsed arguments to check.
     * @return {@code true} if both the file path and format arguments are present, {@code false} otherwise.
     */
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
