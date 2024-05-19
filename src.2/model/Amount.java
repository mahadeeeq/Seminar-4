package model;

/**
 * Represents a monetary amount in a financial or sales application.
 * This class provides functionality to manipulate and compare amounts.
 */
public final class Amount {
    private final int amount;  // Stored as an integer value of cents for precision.

    /**
     * Constructs an Amount with a default value of zero.
     */
    public Amount() {
        this(0);
    }

    /**
     * Constructs an Amount with the specified value.
     *
     * @param amount The monetary value represented in cents.
     */
    public Amount(int amount) {
        this.amount = amount;
    }

    /**
     * Subtracts another Amount from this Amount and returns the result.
     *
     * @param other The Amount to subtract from this instance.
     * @return A new Amount representing the result of the subtraction.
     */
    public Amount minus(Amount other) {
        return new Amount(this.amount - other.amount);
    }

    /**
     * Adds another Amount to this Amount and returns the result.
     *
     * @param other The Amount to add to this instance.
     * @return A new Amount representing the result of the addition.
     */
    public Amount plus(Amount other) {
        return new Amount(this.amount + other.amount);
    }

    /**
     * Multiplies this Amount by a specified factor and returns the result.
     *
     * @param factor The multiplier.
     * @return A new Amount representing the result of the multiplication.
     */
    public Amount multiply(double factor) {
        return new Amount((int) (this.amount * factor));
    }

    /**
     * Compares this Amount to another object to determine equality.
     * Two Amount instances are considered equal if they represent the same amount of money.
     *
     * @param other The object to compare this Amount against.
     * @return true if the other object is an instance of Amount and represents the same amount of money.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Amount that = (Amount) other;
        return this.amount == that.amount;
    }

    /**
     * Returns a string representation of the amount formatted in Swedish currency (SEK).
     * The format used is "XXX kr", where XXX is the integer amount in Swedish krona (SEK).
     * 
     * @return A formatted string representing the monetary amount.
     */
    @Override
    public String toString() {
        return String.format("%d kr", amount / 100);  // Convert cents to kronor for display.
    }
}
