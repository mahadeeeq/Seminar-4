package main.integration;

import main.model.Amount;
import main.model.DatabaseFailureException;
import main.model.ItemDTO;
import main.model.ItemNotFoundException;
import main.model.SaleDTO;

public class ExternalInventorySystem {
    private ItemDTO[] itemArray;

    public ExternalInventorySystem() {
        this.itemArray = createItemArray();
    }

    private ItemDTO[] createItemArray() {
        return new ItemDTO[] {
                new ItemDTO("Cornflakes", "abc123", new Amount(3000), "Cornflakes 500 g, high fiber, gluten free", 6),
                new ItemDTO("Strawberry", "def456", new Amount(1400), "Strawberry 240 g ", 6),
        };
    }

    public ItemDTO findItem(String itemIdentifier) throws ItemNotFoundException, DatabaseFailureException {
        // Simulate database failure for a specific identifier
        if (itemIdentifier.equals("db_error")) {
            throw new DatabaseFailureException("Simulated database failure for item identifier 'db_error'.");
        }

        for (ItemDTO item : itemArray) {
            if (item.getIdentifier().equals(itemIdentifier)) {
                return item;
            }
        }
        throw new ItemNotFoundException(itemIdentifier);
    }

    public void update(SaleDTO saleDTO) {
        System.out.println("Inventory updated for sale with total amount: " + saleDTO.getRunningTotal());
    }
}