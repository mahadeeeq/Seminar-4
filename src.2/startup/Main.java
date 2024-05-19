package startup;

import controller.Controller;
import integration.ExternalSystemCreator;
import integration.Printer;
import view.View;
import view.TotalRevenueView;
import log.TotalRevenueFileOutput;
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