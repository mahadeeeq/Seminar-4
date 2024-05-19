package test.view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import integration.ExternalSystemCreator;
import integration.Printer;

public class ViewTest {

    private View instanceToTest;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        ExternalSystemCreator creator = new ExternalSystemCreator();
        Printer printer = new Printer();
        Controller contr = new Controller(creator, printer);
        instanceToTest = new View(contr);

        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    public void tearDown() {
        instanceToTest = null;

        printoutBuffer = null;
        System.setOut(originalSysOut);
    }

    @Test
    public void testRunFakeExecution() {
        instanceToTest.runFakeExecution();
        String printout = printoutBuffer.toString();
        String expectedStartOutput = "started"; 
        String expectedAddItemOutput = "Add 1 item with item id"; 
        String expectedTotalCost = "Total cost (incl VAT): 76 kr";
        String expectedChange = "Change to give the customer: 23 kr";

        assertTrue(printout.contains(expectedStartOutput), 
                "UI did not start correctly.");
        assertTrue(printout.contains(expectedAddItemOutput), 
                "Item was not added correctly.");
        assertTrue(printout.contains(expectedTotalCost), 
                "Total cost was not calculated correctly.");
        assertTrue(printout.contains(expectedChange), 
                "Change was not calculated correctly.");
    }
}
