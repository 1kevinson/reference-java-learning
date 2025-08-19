package bank;

import java.math.BigDecimal;

public interface AccountService {
    Operation deposit(int accountId, BigDecimal amount) throws NegativeAmountException;

    Operation withdraw(int accountId, BigDecimal amount) throws NegativeAmountException, InsufficientBalanceException;

    void printStatement(StatementPrinter printer, int accountId);
}

