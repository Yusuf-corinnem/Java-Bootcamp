import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();

        User user1 = new User("Alice", 1000);
        User user2 = new User("Bob", 500);
        User user3 = new User("Charlie", 300);

        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);

        service.performTransferTransaction(user1.getId(), user2.getId(), 100);
        service.performTransferTransaction(user2.getId(), user3.getId(), 200);
        service.performTransferTransaction(user1.getId(), user3.getId(), 50);

        UUID manualTransactionId = UUID.randomUUID();
        Transaction unpairedTransaction = new Transaction(manualTransactionId, user1, user2, Transaction.Category.DEBIT, 100);
        user1.getTransactionsList().addTransaction(unpairedTransaction);
        service.getTransactionsList().addTransaction(unpairedTransaction);

        Object[] unpairedTransactions = service.checkTransactions();

        System.out.println("Unpaired Transactions:");
        for (Object transaction : unpairedTransactions) {
            System.out.println(transaction);
        }
        System.out.println();

        System.out.println("Transactions:");
        for (int i = 0; i < service.getTransactionsList().getSize(); ++i) {
            System.out.println("Transaction " + i + ": " + service.getTransactionsList().getTransaction(i) + ", id: " + service.getTransactionsList().getTransaction(i).getId());
            System.out.println("Category: " + service.getTransactionsList().getTransaction(i).getTransferCategory());
            System.out.println("Recipient: " + service.getTransactionsList().getTransaction(i).getRecipient().getName());
            System.out.println("Sender: " + service.getTransactionsList().getTransaction(i).getSender().getName());
            System.out.println();
        }
    }
}
