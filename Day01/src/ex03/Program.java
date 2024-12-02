import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Alice", 300);
        User user2 = new User("Bob", 200);
        TransactionsLinkedList transactionsList = new TransactionsLinkedList();
        Transaction transaction1 = new Transaction(UUID.randomUUID(), user1, user2, Transaction.Category.DEBIT, 100);
        Transaction transaction2 = new Transaction(UUID.randomUUID(), user2, user1, Transaction.Category.CREDIT, -50);
        Transaction transaction3 = new Transaction(UUID.randomUUID(), user1, user2, Transaction.Category.DEBIT, 200);

        transactionsList.addTransaction(transaction1);
        transactionsList.addTransaction(transaction2);
        transactionsList.addTransaction(transaction3);

        System.out.println("All transactions:");
        for (Object transaction : transactionsList.TransformIntoArray()) {
            System.out.println(transaction);
        }

        try {
            transactionsList.removeTransactionById(transaction2.getId());
            System.out.println("\nTransaction with ID " + transaction2.getId() + " has been removed.");
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nAll transactions after removal:");
        for (Object transaction : transactionsList.TransformIntoArray()) {
            System.out.println(transaction);
        }
    }
}
