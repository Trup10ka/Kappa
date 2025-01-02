package com.trup10ka.kappa;

import com.trup10ka.kappa.cli.InsertCustomerAction;
import com.trup10ka.kappa.cli.KappaAction;

import java.util.Map;

public class CliClient
{

    private final Map<Integer, KappaAction> actions = Map.of(
            1, new InsertCustomerAction()
    );

    public void start()
    {
        // Start a loop that check for user input, then takes it and does something with it, implement it

        while (true)
        {


        }
    }
}
