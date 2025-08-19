package bank;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(final String message) {
        super(message);
    }
}
