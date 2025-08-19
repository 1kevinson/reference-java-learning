package bank;

import java.math.BigDecimal;

public final class CheckingAccountService implements AccountService {

    private final AccountRepository accountRepository;
    private final TimeService timeService;

    public CheckingAccountService(final AccountRepository accountRepository, final TimeService timeService) {
        this.accountRepository = accountRepository;
        this.timeService = timeService;
    }

    @Override
    public Operation deposit(final int accountId, final BigDecimal amount) throws NegativeAmountException {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException("Not allow to use negative amount");
        }

        final var balance = getBalance(accountId);

        final var operation = new Operation(OperationType.DEPOSIT, timeService.getCurrentTime(), amount, balance.add(amount));

        this.accountRepository.add(accountId, operation);

        return operation;
    }


    @Override
    public Operation withdraw(final int accountId, final BigDecimal amount) throws NegativeAmountException, InsufficientBalanceException {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException("Not allow to use negative amount");
        }

        final var balance = getBalance(accountId);

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Your balance is insufficient to perform this operation");
        }

        final var operation = new Operation(OperationType.WITHDRAWAL, timeService.getCurrentTime(), amount, balance.subtract(amount));

        this.accountRepository.add(accountId, operation);

        return operation;
    }

    private BigDecimal getBalance(final int accountId) {
        return this.accountRepository.findLast(accountId).map(Operation::getBalance).orElse(BigDecimal.ZERO);
    }

    @Override
    public void printStatement(final StatementPrinter printer, final int accountId) {
        printer.printAccountStatement(this.accountRepository.findAll(accountId));
    }
}
