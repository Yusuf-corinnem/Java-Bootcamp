import java.util.LinkedList;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    LinkedList<Transaction> transactions = new LinkedList<>();

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void removeTransactionById(UUID id) throws TransactionNotFoundException {
        for (int i = 0; i < transactions.size(); ++i) {
            if (transactions.get(i).getId().equals(id)) {
                transactions.remove(transactions.get(i));
                return;
            }
        }

        throw new TransactionNotFoundException("Non-existent identifier");
    }

    @Override
    public Object[] TransformIntoArray() {
        return transactions.toArray();
    }
}
