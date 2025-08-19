package bank;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    void add(int accountId, Operation operation);

    Optional<Operation> findLast(int accountId);

    List<Operation> findAll(int accountId);
}
