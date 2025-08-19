package bank;

import java.io.PrintStream;
import java.util.List;

public class StreamStatementPrinter implements StatementPrinter {

    private final AccountStatementFormatter formatter;
    private final PrintStream printStream;

    public StreamStatementPrinter(final AccountStatementFormatter formatter, final PrintStream printStream) {
        this.formatter = formatter;
        this.printStream = printStream;
    }

    @Override
    public void printAccountStatement(final List<Operation> operations) {
        printStream.print(formatter.format(operations));
    }
}
