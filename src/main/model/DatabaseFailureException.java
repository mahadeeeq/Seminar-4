package main.model;

/**
 * Exception thrown when there is a database failure.
 */
public class DatabaseFailureException extends Exception {
    /**
     * Creates a new instance with a specified error message.
     *
     * @param message The error message.
     */
    public DatabaseFailureException(String message) {
        super(message);
    }
}