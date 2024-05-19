package integration;

/**
 * Initializes and manages references to external systems like accounting and inventory.
 * This class serves as a factory for creating and providing access to external system instances.
 */
public class ExternalSystemCreator {
    private ExternalAccountingSystem externalAccountingSystem;
    private ExternalInventorySystem externalInventorySystem;

    /**
     * Constructs a new ExternalSystemCreator and initializes external systems.
     */
    public ExternalSystemCreator() {
        this.externalAccountingSystem = new ExternalAccountingSystem();
        this.externalInventorySystem = new ExternalInventorySystem();
    }

    /**
     * Retrieves the instance of the ExternalAccountingSystem.
     * 
     * @return The initialized ExternalAccountingSystem.
     */
    public ExternalAccountingSystem getExternalAccountingSystem() {
        return this.externalAccountingSystem;
    }

    /**
     * Retrieves the instance of the ExternalInventorySystem.
     * 
     * @return The initialized ExternalInventorySystem.
     */
    public ExternalInventorySystem getExternalInventorySystem() {
        return this.externalInventorySystem;
    }
}
