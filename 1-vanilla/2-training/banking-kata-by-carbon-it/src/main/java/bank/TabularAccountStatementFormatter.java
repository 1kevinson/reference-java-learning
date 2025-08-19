package bank;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TabularAccountStatementFormatter implements AccountStatementFormatter {

    private static final int CELL_PADDING = 12;
    private static final String DATE = String.format("%1$-" + CELL_PADDING + "s", "date");
    private static final String TYPE = String.format("%1$-" + CELL_PADDING + "s", "type");
    private static final String AMOUNT = String.format("%1$-" + CELL_PADDING + "s", "amount");
    private static final String BALANCE = String.format("%1$-" + CELL_PADDING + "s", "balance");

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private final String tabularHeader;

    public TabularAccountStatementFormatter() {
        this.tabularHeader = String.format("|| %s || %s || %s || %s ||", DATE, TYPE, AMOUNT, BALANCE);
    }

    @Override
    public String format(final List<Operation> operations) {
        final var stringBuilder = new StringBuilder();
        stringBuilder.append(this.tabularHeader);
        stringBuilder.append(System.lineSeparator());

        final var formattedOperations = operations.stream()
                .sorted(Comparator.comparing(Operation::getDate).reversed())
                .map(this::formatOperationStatement)
                .collect(Collectors.joining(System.lineSeparator()));

        return stringBuilder.append(formattedOperations).toString();
    }

    private String formatOperationStatement(final Operation operation) {
        final var dateFormattedString = formatDateValue(operation.getDate());
        final var operationTypeFormattedString = formatTypeValue(operation.getType());
        final var amountFormattedString = formatAmountValue(operation.getAmount());
        final var balanceFormattedString = formatBigDecimalValue(operation.getBalance());
        return String.format("|| %s || %s || %s || %s ||", dateFormattedString, operationTypeFormattedString, amountFormattedString, balanceFormattedString);
    }

    private String formatAmountValue(final BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0 ? padRight("", CELL_PADDING) : formatBigDecimalValue(value);
    }

    private String formatBigDecimalValue(final BigDecimal value) {
        return padRight(decimalFormat.format(value), CELL_PADDING);
    }

    private String formatTypeValue(final OperationType value) {
        return padRight(value.toString(), CELL_PADDING);
    }

    private String formatDateValue(final LocalDateTime value) {
        return padRight(dateTimeFormatter.format(value), CELL_PADDING);
    }

    private String padRight(final String value, final int padding) {
        return String.format("%1$-" + padding + "s", value);
    }

}
