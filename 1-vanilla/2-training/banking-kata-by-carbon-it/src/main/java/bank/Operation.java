package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Operation {
    private final OperationType type;
    private final LocalDateTime date;
    private final BigDecimal amount;
    private final BigDecimal balance;

    public Operation(final OperationType type, final LocalDateTime date, final BigDecimal amount, final BigDecimal balance) {
        this.type = type;
        this.date = date;
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
        this.balance = balance.setScale(2, RoundingMode.HALF_EVEN);
    }

    public OperationType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var operation = (Operation) o;
        return type == operation.type &&
                Objects.equals(date, operation.date) &&
                Objects.equals(amount, operation.amount) &&
                Objects.equals(balance, operation.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, date, amount, balance);
    }
}
