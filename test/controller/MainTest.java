package test.startup;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import startup.Main;

public class MainTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        originalSysOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalSysOut);
        outContent = null;
    }

    @Test
    public void testMainExecution() {
        String[] args = null;
        Main.main(args);
        String output = outContent.toString();

        assertTrue(output.contains("Add 1 item with item id abc123:"), "First item was not added correctly.");
        assertTrue(output.contains("Add 1 item with item id def456:"), "Second item was not added correctly.");
        assertTrue(output.contains("End sale:"), "Sale was not ended correctly.");
        assertTrue(output.contains("Customer pays"), "Payment was not processed correctly.");
        assertTrue(output.contains("Change to give the customer"), "Change was not calculated correctly.");
    }
}
