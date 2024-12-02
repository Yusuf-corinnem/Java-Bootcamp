import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "John", 300);
        User user2 = new User(2, "Mike", 200);
        Transaction trans = new Transaction(UUID.randomUUID(), user1, user2, Transaction.Category.DEBIT, 100);
        System.out.println(user1.getName() + " (id: " + user1.getId() + ", balance: " + user1.getBalance() + ")");
        System.out.println(user2.getName() + " (id: " + user2.getId() + ", balance: " + user2.getBalance() + ")");
        System.out.println("Transaction (id: " + trans.getId() + ", recipient: " + trans.getRecipient().getName() + ", sender: " + trans.getSender().getName() + ", category: " + trans.getTransferCategory() + ", amount: " + trans.getTransferAmount() + ")");
    }
}
