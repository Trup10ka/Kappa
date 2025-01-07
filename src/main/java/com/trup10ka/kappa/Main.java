package com.trup10ka.kappa;

import com.trup10ka.kappa.cli.CliClient;

public class Main
{
    public static void main(String[] args)
    {
        CliClient cliClient = new CliClient();

        cliClient.init();
        cliClient.start();
    }
}
