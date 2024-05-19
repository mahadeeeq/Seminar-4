package view;

import model.Amount;
import model.SaleObserver;

public class TotalRevenueView implements SaleObserver {
    private Amount totalRevenue = new Amount();

    @Override
    public void newSale(Amount amount) {
        totalRevenue = totalRevenue.plus(amount);
        printCurrentRevenue();
    }

    private void printCurrentRevenue() {
        System.out.println("Total Revenue: " + totalRevenue);
    }
}