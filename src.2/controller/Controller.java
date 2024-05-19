package controller;

import integration.ExternalAccountingSystem;
import integration.ExternalInventorySystem;
import integration.ExternalSystemCreator;
import integration.Printer;
import model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Central control point for handling sale transactions and interactions with external systems.
 */
public class Controller {
    private Printer printer;
    private ExternalAccountingSystem accountingSystem;
    private ExternalInventorySystem inventorySystem;
    private CashRegister cashRegister;
    private Sale sale;
    private List<SaleObserver> saleObservers = new ArrayList<>();

    /**
     * Initializes the controller with external system access and a printer.
     *
     * @param creator Provides access to external systems.
     * @param printer Used for printing receipts.
     */
    public Controller(ExternalSystemCreator creator, Printer printer) {
        this.printer = printer;
        this.accountingSystem = creator.getExternalAccountingSystem();
        this.inventorySystem = creator.getExternalInventorySystem();
        this.cashRegister = new CashRegister();
    }

    /**
     * Prepares the system for a new sale.
     */
    public void startSale() {
        sale = new Sale();
        for (SaleObserver observer : saleObservers) {
            sale.addSaleObserver(observer);
        }
    }

    /**
     * Adds an item to the current sale based on its identifier.
     *
     * @param itemIdentifier Unique identifier of the item.
     * @return Details of the item and the updated sale total.
     */
    public ItemAndRunningTotalDTO enterIdentifier(String itemIdentifier) throws ItemNotFoundException, DatabaseFailureException {
        ItemDTO item = inventorySystem.findItem(itemIdentifier);
        return sale.registerItem(item);
    }

    /**
     * Finalizes the sale and calculates the total cost.
     *
     * @return The total price of the sale including VAT.
     */
    public Amount endSale() {
        return sale.getRunningTotal();
    }

    /**
     * Processes payment for the sale and handles post-payment actions including printing receipts.
     *
     * @param paidAmount The amount paid by the customer.
     * @return The change to be returned to the customer.
     */
    public Amount pay(Amount paidAmount) {
        CashPayment payment = new CashPayment(paidAmount, sale);
        
        // Record the sale in external systems.
        SaleDTO saleDetails = new SaleDTO(sale);
        accountingSystem.update(saleDetails);
        inventorySystem.update(saleDetails);

        // Print receipt and update cash register.
        Receipt receipt = new Receipt(sale, payment);
        printer.printReceipt(receipt);
        cashRegister.addPayment(sale.getRunningTotal());

        sale.finalizeSale();

        return payment.getChange();
    }

    /**
     * Adds a sale observer to be notified of new sales.
     *
     * @param observer The observer to add.
     */
    public void addSaleObserver(SaleObserver observer) {
        saleObservers.add(observer);
    }
}
