package bank;

import java.util.List;

public interface AccountStatementFormatter {
    String format(List<Operation> operations);
}
