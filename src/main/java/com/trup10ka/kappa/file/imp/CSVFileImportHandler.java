package com.trup10ka.kappa.file.imp;

import com.trup10ka.kappa.data.Customer;
import com.trup10ka.kappa.data.CustomerSex;
import com.trup10ka.kappa.data.Product;
import com.trup10ka.kappa.util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.trup10ka.kappa.util.FileUtil.createParentDirectoriesIfNotExists;

public class CSVFileImportHandler implements ImportHandler
{

    @Override
    public List<Customer> importCustomers(String filePath)
    {
        List<Customer> customers = new ArrayList<>();

        File file = new File(IMPORT_FOLDER + filePath);
        createParentDirectoriesIfNotExists(file);

        if (!file.exists())
        {
            System.out.println("File does not exist: " + filePath);
            return customers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null)
                parseLine(customers, line);

        }
        catch (IOException e)
        {
            System.err.println("Error reading file: " + filePath);
        }

        return customers;
    }

    @Override
    public List<String> importProducts(String filePath)
    {

        return new ArrayList<>();
    }

    private void parseLine(List<Customer> customers, String line)
    {
        String[] parts = line.split(",");
        if (parts.length != 4)
        {
            System.err.println("Invalid line: " + line);
            return;
        }
        try
        {
            customers.add(createCustomerFromParts(parts));
        }
        catch (IllegalArgumentException e)
        {
            handleExceptions(e, line);
        }
    }

    private Customer createCustomerFromParts(String[] parts) throws IllegalArgumentException
    {
        String firstName = parts[0].trim();
        String lastName = parts[1].trim();
        CustomerSex sex = CustomerSex.valueOf(parts[2].trim().toUpperCase());
        int customerCredits = Integer.parseInt(parts[3].trim());
        return new Customer(firstName, lastName, sex, customerCredits);
    }

    private void handleExceptions(IllegalArgumentException e, String line)
    {
        if (e instanceof NumberFormatException)
            System.err.println("Invalid customer credits number format in line: " + line);

        else
            System.err.println("Invalid sex on line: " + line);
    }
}
