package integration;

import model.Amount;
import model.ItemDTO;
import model.ItemNotFoundException;
import model.DatabaseFailureException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExternalInventorySystemTest {
    private ExternalInventorySystem instanceToTest;

    @BeforeEach
    public void setUp() {
        instanceToTest = new ExternalInventorySystem();
    }

    @AfterEach
    public void tearDown() {
        instanceToTest = null;
    }

    @Test
    public void testFindItemExists() throws ItemNotFoundException, DatabaseFailureException {
        String itemIdentifier = "abc123";
        String expResult = itemIdentifier;
        ItemDTO result = instanceToTest.findItem(itemIdentifier);
        assertNotNull(result, "Expected a non-null item.");
        assertEquals(expResult, result.getIdentifier(), "Existing ItemDTO not found.");
    }

    @Test
    public void testFindItemNotFound() {
        String itemIdentifier = "invalidItem";
        assertThrows(ItemNotFoundException.class, () -> {
            instanceToTest.findItem(itemIdentifier);
        }, "Expected findItem() to throw ItemNotFoundException, but it didn't.");
    }

    @Test
    public void testFindItemThrowsDatabaseFailureException() {
        String itemIdentifier = "db_error";
        assertThrows(DatabaseFailureException.class, () -> {
            instanceToTest.findItem(itemIdentifier);
        }, "Expected findItem() to throw DatabaseFailureException, but it didn't.");
    }
}
