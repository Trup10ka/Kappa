package com.trup10ka.kappa.file.exp;

import com.trup10ka.kappa.data.export.MostOrderedProduct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static com.trup10ka.kappa.util.FileUtil.createParentDirectoriesIfNotExists;

public class CSVFileExportHandler implements ExportHandler
{

    private final String filePath;

    public CSVFileExportHandler(String path)
    {
        this.filePath = path;
    }

    @Override
    public boolean exportMostOrderedItems(List<MostOrderedProduct> mostOrderedProducts)
    {
        File file = new File(filePath);
        createParentDirectoriesIfNotExists(file.getParentFile());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            writer.write(String.join(",", getFieldsOfObjectsAsIterable(new MostOrderedProduct("", 0))));
            writer.newLine();

            for (MostOrderedProduct c : mostOrderedProducts)
            {
                writeRecordToFile(writer, c);
            }
            return true;
        }
        catch (IOException e)
        {
            resolveExceptionOccurred(e);
            return false;
        }
    }

    private void writeRecordToFile(BufferedWriter writer, Object object) throws IOException
    {
        writer.write(String.join(",", getFieldValuesAsIterable(object)));
        writer.newLine();
    }

    private Iterable<String> getFieldsOfObjectsAsIterable(Object object)
    {
        return () -> Arrays.stream(object.getClass().getDeclaredFields())
                .map(Field::getName)
                .iterator();
    }

    private Iterable<String> getFieldValuesAsIterable(Object object)
    {
        return ()  -> Arrays.stream(object.getClass().getDeclaredFields())
                .map(field -> {
                    try
                    {
                        field.setAccessible(true);
                        return field.get(object).toString();
                    }
                    catch (IllegalAccessException e)
                    {
                        resolveExceptionOccurred(e);
                        return null;
                    }
                })
                .iterator();
    }

    private void resolveExceptionOccurred(Exception e)
    {
        if (e instanceof IllegalAccessException)
        {
            System.err.println("Failed to access the field of the record");
        }
        else if (e instanceof IOException)
        {
            System.err.println("Failed to write to the file");
        }
    }

}
