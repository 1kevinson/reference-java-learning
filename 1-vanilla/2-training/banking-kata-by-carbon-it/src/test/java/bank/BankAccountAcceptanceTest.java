package bank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class BankAccountAcceptanceTest {

    final String expectedOperations = String.join(System.lineSeparator(),
            "|| date         || type         || amount       || balance      ||",
            "|| 05/05/2015   || DEPOSIT      || 1000,00      || 1000,00      ||",
            "|| 05/05/2015   || DEPOSIT      || 1000,00      || 1000,00      ||",
            "|| 05/05/2015   || DEPOSIT      || 2000,00      || 3000,00      ||",
            "|| 05/05/2015   || WITHDRAWAL   || 500,00       || 500,00       ||",
            "|| 05/05/2015   || WITHDRAWAL   || 200,00       || 800,00       ||"
    );

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private final PrintStream printStream = new PrintStream(outputStream);

    private static final int accountId = 8;

    private PrintStream originalOut;

    private final TimeService timeService = () -> LocalDateTime.of(LocalDate.of(2015, 5, 5), LocalTime.of(10, 15, 30));

    @BeforeEach
    void setup() {
        originalOut = System.out;
        System.setOut(printStream);
    }

    @Test
    void shouldPrintCorrectOperationsForAcceptance() throws NegativeAmountException, InsufficientBalanceException {
        // Arrange
        final var accountService = new CheckingAccountService(new InMemoryAccountRepository(), timeService);
        final StatementPrinter statementPrinter = new StreamStatementPrinter(new TabularAccountStatementFormatter(), printStream);

        // Act
        accountService.deposit(accountId, BigDecimal.valueOf(1000));
        accountService.deposit(accountId, BigDecimal.valueOf(2000));
        accountService.withdraw(accountId, BigDecimal.valueOf(500));
        accountService.withdraw(accountId, BigDecimal.valueOf(200));

        accountService.printStatement(statementPrinter, accountId);

        // Assert
        Assertions.assertEquals(expectedOperations, outputStream.toString());
    }

    @AfterEach
    void teardown() {
        System.setOut(originalOut);
    }

    static class InMemoryAccountRepository implements AccountRepository {
        final Map<Integer, List<Operation>> accountDatas = new HashMap<>();

        @Override
        public void add(final int accountId, final Operation operation) {
            accountDatas.putIfAbsent(accountId, new ArrayList<>(Collections.singletonList(operation)));
            accountDatas.computeIfPresent(accountId, (key, operationList) -> {
                operationList.add(operation);
                return operationList;
            });
        }

        @Override
        public Optional<Operation> findLast(final int accountId) {
            return this.findAll(accountId).stream().max(Comparator.comparing(Operation::getDate));
        }

        @Override
        public List<Operation> findAll(final int accountId) {
            return accountDatas.getOrDefault(accountId, List.of());
        }
    }
}
