package integration;

import model.Amount;
import model.CashPayment;
import model.ItemDTO;
import model.Receipt;
import model.Sale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalSysOut;
    private Sale sale;
    private CashPayment payment;
    private Receipt receipt;
    private Printer instance;

    @BeforeEach
    public void setUp() {
        originalSysOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Amount paidAmount = new Amount(10000);
        sale = new Sale();
        ItemDTO item = new ItemDTO("Cornflakes", "abc123", new Amount(3000), "Cornflakes 500 g, high fiber, gluten free", 6);
        try {
            sale.registerItem(item);
        } catch (Exception e) {
            fail("Exception thrown during item registration: " + e.getMessage());
        }
        payment = new CashPayment(paidAmount, sale);
        receipt = new Receipt(sale, payment);
        instance = new Printer();
    }

    @AfterEach
    public void tearDown() {
        outContent = null;
        System.setOut(originalSysOut);

        sale = null;
        payment = null;
        receipt = null;
        instance = null;
    }

    @Test
    public void testPrintReceipt() {
        instance.printReceipt(receipt);
        String output = outContent.toString();
        assertTrue(output.contains("BEGIN RECEIPT"), "Output does not contain 'BEGIN RECEIPT'.");
        assertTrue(output.contains("Cornflakes"), "Output does not contain item name 'Cornflakes'.");
        assertTrue(output.contains("3000 kr"), "Output does not contain item price '3000 kr'.");
        assertTrue(output.contains("END RECEIPT"), "Output does not contain 'END RECEIPT'.");
    }

    @Test
    public void testPrintReceiptForTotalPrice() {
        instance.printReceipt(receipt);
        String output = outContent.toString();
        String expectedOutput = sale.getRunningTotal().toString();
        assertTrue(output.contains(expectedOutput), "Output does not contain total price.");
    }

    @Test
    public void testPrintReceiptForPaidAmount() {
        instance.printReceipt(receipt);
        String output = outContent.toString();
        String expectedOutput = payment.getPaidAmount().toString();
        assertTrue(output.contains(expectedOutput), "Output does not contain paid amount.");
    }

    @Test
    public void testPrintReceiptForChange() {
        instance.printReceipt(receipt);
        String output = outContent.toString();
        String expectedOutput = payment.getChange().toString();
        assertTrue(output.contains(expectedOutput), "Output does not contain change.");
    }
}
