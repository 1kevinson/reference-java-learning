package bank;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TabularAccountStatementFormatterTest {

    @Test
    void shouldFormatAccountStatementWithOperationsOrderedByDateAsAString() {
        final var operations = List.of(
                new Operation(OperationType.DEPOSIT, LocalDateTime.of(LocalDate.of(2015, 5, 5), LocalTime.of(10, 15, 30)), BigDecimal.ONE, BigDecimal.TEN),
                new Operation(OperationType.DEPOSIT, LocalDateTime.of(LocalDate.of(2015, 5, 15), LocalTime.of(10, 15, 30)), BigDecimal.ONE, BigDecimal.TEN),
                new Operation(OperationType.DEPOSIT, LocalDateTime.of(LocalDate.of(2015, 6, 8), LocalTime.of(10, 15, 30)), BigDecimal.ONE, BigDecimal.TEN)
        );
        final var tabularAccountStatementFormatter = new TabularAccountStatementFormatter();

        final var expected = String.join(System.lineSeparator(),
                "|| date         || type         || amount       || balance      ||",
                "|| 08/06/2015   || DEPOSIT      || 1,00         || 10,00        ||",
                "|| 15/05/2015   || DEPOSIT      || 1,00         || 10,00        ||",
                "|| 05/05/2015   || DEPOSIT      || 1,00         || 10,00        ||"
        );

        final var actual = tabularAccountStatementFormatter.format(operations);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFormatAccountStatementAsAStringWithNoOperations() {
        final var tabularAccountStatementFormatter = new TabularAccountStatementFormatter();

        final var expected = "|| date         || type         || amount       || balance      ||" + System.lineSeparator();

        final var actual = tabularAccountStatementFormatter.format(List.of());

        assertEquals(expected, actual);
    }
}
