package bank;

import java.util.List;

public interface StatementPrinter {
   void printAccountStatement(List<Operation> operations);
}
