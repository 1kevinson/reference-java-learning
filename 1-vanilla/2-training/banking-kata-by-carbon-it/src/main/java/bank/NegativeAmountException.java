package bank;

public class NegativeAmountException extends Exception {
    public NegativeAmountException(final String message) {
        super(message);
    }
}
