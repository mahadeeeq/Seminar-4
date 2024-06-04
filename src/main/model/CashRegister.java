package main.model;

/**
 * Represents the cash register in the point of sale system. Keeps track of available balance.
 */
public class CashRegister {
    private Amount balance;  // Stores the current monetary balance in the cash register.

    /**
     * Creates a new instance of CashRegister. Initializes the balance to 0.
     */
    public CashRegister(){
        this.balance = new Amount(0);  // Initialize balance to zero.
    }
    
    /**
     * Adds the payment received from a sale to the cash register's balance.
     *
     * @param amountAdded the Amount object representing the monetary value added by the sale.
     */
    public void addPayment(Amount amountAdded){
        this.balance = this.balance.plus(amountAdded);  // Add the payment to the current balance.
    }
    
    /**
     * Retrieves the current balance of the cash register.
     *
     * @return the current balance as an Amount.
     */
    public Amount getBalance(){
        return this.balance;
    }
}
