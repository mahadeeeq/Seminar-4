package model;
/**
 * Exception thrown when an item is not found in the inventory.
 */
public class ItemNotFoundException extends Exception {
    /**
     * Creates a new instance with a specified error message.
     *
     * @param itemId The identifier of the item that was not found.
     */
    public ItemNotFoundException(String itemId) {
        super("Item with identifier '" + itemId + "' not found.");
    }
}


