package main.model;

/**
 * Represents a financial transaction associated with a sale.
 * This class manages the payment details, including the amount paid and the change due.
 */
public class CashPayment {
    private final Amount paidAmount;  // The total amount paid by the customer.
    private final Amount change;      // The change to be returned to the customer.

    /**
     * Initializes a new instance of CashPayment.
     * 
     * @param paidAmount The amount paid by the customer.
     * @param sale The sale for which the payment is made.
     */
    public CashPayment(Amount paidAmount, Sale sale) {
        this.paidAmount = paidAmount;
        this.change = calculateChange(paidAmount, sale);
    }

    /**
     * Calculates the change to be returned based on the amount paid and the total of the sale.
     * 
     * @param paidAmount The amount paid by the customer.
     * @param sale The sale transaction for which the payment is made.
     * @return The amount of change to be returned to the customer.
     */
    private Amount calculateChange(Amount paidAmount, Sale sale) {
        return paidAmount.minus(sale.getRunningTotal());
    }

    /**
     * Retrieves the amount paid by the customer.
     * 
     * @return The total amount paid.
     */
    public Amount getPaidAmount() {
        return this.paidAmount;
    }

    /**
     * Retrieves the change due to the customer.
     * 
     * @return The change to be returned.
     */
    public Amount getChange() {
        return this.change;
    }
}
