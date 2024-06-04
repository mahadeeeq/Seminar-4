package main.integration;

import main.model.Receipt;

/**
 * Manages the process of printing receipts. 
 * For simulation purposes, receipt printing is represented by outputting to the standard system console.
 */
public class Printer {

    /**
     * Outputs the formatted receipt details to the console, simulating the act of printing.
     * 
     * @param receipt The receipt object that contains the details to be printed.
     */
    public void printReceipt(Receipt receipt) {
        System.out.println(receipt);  // Implicitly calls receipt.toString()
    }
}
