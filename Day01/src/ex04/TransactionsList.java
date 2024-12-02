import java.util.UUID;

public interface TransactionsList {
    void addTransaction(Transaction transaction);

    void removeTransactionById(UUID id);

    Object[] TransformIntoArray();
}
