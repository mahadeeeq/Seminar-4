package main.startup;

import main.controller.Controller;
import main.integration.ExternalSystemCreator;
import main.integration.Printer;
import main.log.TotalRevenueFileOutput;
import main.view.TotalRevenueView;
import main.view.View;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        ExternalSystemCreator systemCreator = new ExternalSystemCreator();
        Printer receiptPrinter = new Printer();
        Controller salesController = new Controller(systemCreator, receiptPrinter);
        View simulationView = new View(salesController);

        TotalRevenueView totalRevenueView = new TotalRevenueView();
        TotalRevenueFileOutput totalRevenueFileOutput;
        try {
            totalRevenueFileOutput = new TotalRevenueFileOutput();
            salesController.addSaleObserver(totalRevenueFileOutput);
        } catch (IOException e) {
            System.out.println("Could not create file logger: " + e.getMessage());
        }
        
        salesController.addSaleObserver(totalRevenueView);

        simulationView.runFakeExecution();
    }
}