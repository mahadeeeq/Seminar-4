package main.view;

import java.io.IOException;

import main.controller.Controller;
import main.log.FileLogger;
import main.model.Amount;
import main.model.DatabaseFailureException;
import main.model.ItemAndRunningTotalDTO;
import main.model.ItemNotFoundException;

public class View {
    private Controller contr;
    private FileLogger logger;

    public View(Controller contr) {
        this.contr = contr;
        try {
            this.logger = new FileLogger();
        } catch (IOException e) {
            System.out.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public void runFakeExecution() {
        contr.startSale();
        addItemToSale("abc123");
        addItemToSale("abc123");
        addItemToSale("def456");
        addItemToSale("xyz789"); // Should trigger ItemNotFoundException
        addItemToSale("db_error"); // Should trigger DatabaseFailureException
        System.out.println("End sale:");
        Amount totalPrice = contr.endSale();
        System.out.println("Total cost (incl VAT): " + totalPrice.toString() + "\n");
        Amount paidAmount = new Amount(10000);
        System.out.println("Customer pays " + paidAmount + ":");
        Amount change = contr.pay(paidAmount);
        System.out.println("Change to give the customer: " + change);
    }

    private void addItemToSale(String itemIdentifier) {
        try {
            ItemAndRunningTotalDTO itemDetails = contr.enterIdentifier(itemIdentifier);
            System.out.println("Add 1 item with item id " + itemIdentifier + ":");
            System.out.println(itemAndRunningTotalDTOString(itemDetails));
        } catch (ItemNotFoundException e) {
            System.out.println("Item not found: " + e.getMessage());
            logger.log(e);
        } catch (DatabaseFailureException e) {
            System.out.println("Database error: " + e.getMessage());
            logger.log(e);
        }
    }

    private String itemAndRunningTotalDTOString(ItemAndRunningTotalDTO item) {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: ").append(item.getItem().getIdentifier()).append("\n");
        builder.append("Item name: ").append(item.getItem().getName()).append("\n");
        builder.append("Item cost: ").append(item.getItem().getPrice().plus(item.getItem().getVat()).toString())
                .append("\n");
        builder.append("VAT: ").append(item.getItem().getVatRate()).append("% \n");
        builder.append("Item description: ").append(item.getItem().getDescription()).append("\n");
        builder.append("\n");
        builder.append("Total cost (incl VAT): ").append(item.getRunningTotal().toString()).append("\n");
        builder.append("Total VAT: ").append(item.getRunningVat().toString()).append("\n");
        return builder.toString();
    }
}
