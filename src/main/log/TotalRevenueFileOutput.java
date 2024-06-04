package main.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import main.model.Amount;
import main.model.SaleObserver;

public class TotalRevenueFileOutput implements SaleObserver {
    private Amount totalRevenue = new Amount();
    private PrintWriter writer;

    public TotalRevenueFileOutput() throws IOException {
        writer = new PrintWriter(new FileWriter("total-revenue.txt", true), true);
    }

    @Override
    public void newSale(Amount amount) {
        totalRevenue = totalRevenue.plus(amount);
        printCurrentRevenueToFile();
    }

    private void printCurrentRevenueToFile() {
        writer.println("Total Revenue: " + totalRevenue);
    }
}