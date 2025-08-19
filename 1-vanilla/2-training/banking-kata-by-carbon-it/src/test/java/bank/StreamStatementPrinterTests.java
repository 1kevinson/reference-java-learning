package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StreamStatementPrinterTests {

    private static final String sampleFormattedString = "test";

    private final LocalDateTime sampleLocaleDateTime = LocalDateTime.of(LocalDate.of(2015, 5, 5), LocalTime.of(10, 15, 30));

    @Mock
    private AccountStatementFormatter formatter;

    @Mock
    private PrintStream printStream;

    @BeforeEach
    void setup() {
        when(formatter.format(any())).thenReturn(sampleFormattedString);
    }

    @Test
    void shouldPrintFormattedStatementInConsole() {
        final var operations = List.of(
                new Operation(OperationType.DEPOSIT, sampleLocaleDateTime, BigDecimal.valueOf(500.0), BigDecimal.valueOf(1000.0)),
                new Operation(OperationType.DEPOSIT, sampleLocaleDateTime, BigDecimal.valueOf(500.0), BigDecimal.valueOf(1500.0)),
                new Operation(OperationType.WITHDRAWAL, sampleLocaleDateTime, BigDecimal.valueOf(500.0), BigDecimal.valueOf(1000.0))
        );
        new StreamStatementPrinter(formatter, printStream).printAccountStatement(operations);

        verify(formatter).format(eq(operations));
        verify(printStream).print(eq(sampleFormattedString));

        verifyNoMoreInteractions(formatter, printStream);
    }

    @Test
    void shouldPrintStatementIfNotOperations() {
        new StreamStatementPrinter(formatter, printStream).printAccountStatement(List.of());

        verify(formatter).format(eq(List.of()));
        verify(printStream).print(eq(sampleFormattedString));

        verifyNoMoreInteractions(formatter, printStream);
    }
}
