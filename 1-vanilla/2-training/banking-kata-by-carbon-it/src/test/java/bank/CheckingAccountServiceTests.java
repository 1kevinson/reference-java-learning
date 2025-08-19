package bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckingAccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StatementPrinter printer;

    @Mock
    private TimeService fixedTimeService;

    private final LocalDateTime sampleLocaleDateTime = LocalDateTime.now(Clock.fixed(Instant.parse("2015-05-05T10:15:30.00Z"), ZoneId.of("UTC")));


    @Test
    void shouldBeAbleToDepositAPositiveAmountOfMoney() throws NegativeAmountException {
        // Arrange
        final var accountId = 7;
        final var amount = BigDecimal.valueOf(100.0);
        final var expected = new Operation(OperationType.DEPOSIT, sampleLocaleDateTime, amount, amount);

        when(fixedTimeService.getCurrentTime()).thenReturn(sampleLocaleDateTime);
        when(accountRepository.findLast(accountId)).thenReturn(Optional.empty());

        // Act
        final var checkingAccountService = new CheckingAccountService(accountRepository, fixedTimeService);
        final var actual = checkingAccountService.deposit(accountId, amount);

        // Assert
        Assertions.assertEquals(expected, actual);

        verify(accountRepository).add(accountId, expected);
        verify(accountRepository).findLast(accountId);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldNotBeAbleToDepositANegativeAmountOfMoney() {
        // Arrange
        final var accountId = 7;
        final var amount = BigDecimal.valueOf(-100.0);

        // Assert / Act
        final var checkingAccountService = new CheckingAccountService(accountRepository, fixedTimeService);

        Assertions.assertThrows(NegativeAmountException.class, () -> checkingAccountService.deposit(accountId, amount));

        // Assert
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldBeAbleToWithdrawAPositiveAmountOfMoneyFromAProvisionedAccount() throws NegativeAmountException, InsufficientBalanceException {
        // Arrange
        final var accountId = 7;
        final var amount = BigDecimal.valueOf(100.0);
        final var expected = new Operation(OperationType.WITHDRAWAL, sampleLocaleDateTime, amount, BigDecimal.ZERO);

        when(fixedTimeService.getCurrentTime()).thenReturn(sampleLocaleDateTime);
        when(accountRepository.findLast(accountId)).thenReturn(Optional.of(new Operation(OperationType.WITHDRAWAL, sampleLocaleDateTime, amount, amount)));

        // Act
        final var checkingAccountService = new CheckingAccountService(accountRepository, fixedTimeService);
        final var actual = checkingAccountService.withdraw(accountId, amount);

        // Assert
        Assertions.assertEquals(expected, actual);

        verify(accountRepository).add(accountId, expected);
        verify(accountRepository).findLast(accountId);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldNotBeAbleToWithdrawAPositiveAmountOfMoneyFromANotSufficientlyProvisionedAccount() {
        // Arrange
        final var accountId = 7;
        final var amount = BigDecimal.valueOf(50);

        // Act
        final var checkingAccountService = new CheckingAccountService(accountRepository, fixedTimeService);
        when(accountRepository.findLast(accountId)).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThrows(InsufficientBalanceException.class,
                () -> checkingAccountService.withdraw(accountId, amount));

        verify(accountRepository).findLast(accountId);
        verifyNoMoreInteractions(accountRepository);

    }

    @Test
    void shouldNotBeAbleToWithdrawANegativeAmountOfMoney() {
        // Arrange
        final var accountId = 7;
        final var amount = BigDecimal.valueOf(-100.0);

        // Assert / Act
        final var checkingAccountService = new CheckingAccountService(accountRepository, fixedTimeService);

        Assertions.assertThrows(NegativeAmountException.class, () -> checkingAccountService.withdraw(accountId, amount));

        // Assert
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldRenderArrayOfOneOrManyOperationInPlainText() {
        // Arrange
        final var accountId = 7;
        final var operations = List.of(
                new Operation(OperationType.DEPOSIT, sampleLocaleDateTime, BigDecimal.valueOf(500), BigDecimal.valueOf(1000)),
                new Operation(OperationType.DEPOSIT, sampleLocaleDateTime, BigDecimal.valueOf(500), BigDecimal.valueOf(1500)),
                new Operation(OperationType.WITHDRAWAL, sampleLocaleDateTime, BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
        );

        when(accountRepository.findAll(accountId)).thenReturn(operations);

        // Act
        final var checkingAccountService = new CheckingAccountService(accountRepository, fixedTimeService);
        checkingAccountService.printStatement(printer, accountId);

        // Assert
        verify(printer).printAccountStatement(operations);
        verify(accountRepository).findAll(accountId);

        verifyNoMoreInteractions(printer);
        verifyNoMoreInteractions(accountRepository);
    }
}
