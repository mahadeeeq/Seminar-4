package controller;

import integration.ExternalSystemCreator;
import integration.Printer;
import model.Amount;
import model.CashRegister;
import model.ItemAndRunningTotalDTO;
import model.ItemDTO;
import model.ItemNotFoundException;
import model.DatabaseFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller contr;
    private Printer printer;
    private ExternalSystemCreator creator;
    private CashRegister cashRegister;

    @BeforeEach
    public void setUp() {
        printer = new Printer();
        creator = new ExternalSystemCreator();
        cashRegister = new CashRegister();
        contr = new Controller(creator, printer);
    }

    @Test
    public void testEnterIdentifierValidIdentifier() {
        contr.startSale();
        String validIdentifier = "abc123";
        ItemDTO expectedResult = new ItemDTO("Cornflakes", "abc123", new Amount(3000), "Cornflakes 500 g, high fiber, gluten free", 6);
        try {
            ItemAndRunningTotalDTO result = contr.enterIdentifier(validIdentifier);
            assertEquals(expectedResult, result.getItem(), "enterIdentifier not returning correct item.");
            assertEquals(expectedResult.getPrice().plus(expectedResult.getVat()), result.getRunningTotal(), "enterIdentifier not returning correct running total.");
        } catch (ItemNotFoundException | DatabaseFailureException e) {
            fail("Exception thrown during valid item lookup: " + e.getMessage());
        }
    }

    @Test
    public void testEnterIdentifierThrowsItemNotFoundException() {
        contr.startSale();
        String invalidIdentifier = "invalidItem";
        assertThrows(ItemNotFoundException.class, () -> {
            contr.enterIdentifier(invalidIdentifier);
        }, "Expected enterIdentifier() to throw ItemNotFoundException, but it didn't.");
    }

    @Test
    public void testEnterIdentifierThrowsDatabaseFailureException() {
        contr.startSale();
        String dbErrorIdentifier = "db_error";
        assertThrows(DatabaseFailureException.class, () -> {
            contr.enterIdentifier(dbErrorIdentifier);
        }, "Expected enterIdentifier() to throw DatabaseFailureException, but it didn't.");
    }

    @Test
    public void testEndSale() {
        contr.startSale();
        ItemDTO item = new ItemDTO("Cornflakes", "abc123", new Amount(3000), "Cornflakes 500 g, high fiber, gluten free", 6);
        try {
            contr.enterIdentifier("abc123");
            contr.enterIdentifier("abc123");
        } catch (ItemNotFoundException | DatabaseFailureException e) {
            fail("Exception thrown during item lookup: " + e.getMessage());
        }
        Amount expectedResult = item.getPrice().plus(item.getVat()).multiply(2);
        Amount result = contr.endSale();
        assertEquals(expectedResult, result, "endSale not returning correct total price.");
    }

    @Test
    public void testPay() {
        contr.startSale();
        ItemDTO item = new ItemDTO("Cornflakes", "abc123", new Amount(3000), "Cornflakes 500 g, high fiber, gluten free", 6);
        try {
            contr.enterIdentifier("abc123");
            contr.enterIdentifier("abc123");
        } catch (ItemNotFoundException | DatabaseFailureException e) {
            fail("Exception thrown during item lookup: " + e.getMessage());
        }
        Amount totalPrice = item.getPrice().plus(item.getVat()).multiply(2);
        Amount amountPaid = new Amount(10000);
        Amount expectedResult = amountPaid.minus(totalPrice);
        Amount result = contr.pay(amountPaid);
        assertEquals(expectedResult, result, "pay not returning correct change.");
    }
}
