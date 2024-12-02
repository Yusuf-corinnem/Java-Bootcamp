import java.util.ArrayList;
import java.util.UUID;

public class TransactionsService {
    private UsersArrayList userList = new UsersArrayList();
    private TransactionsLinkedList transactionsList = new TransactionsLinkedList();

    public Integer getBalance(User user) {
        return userList.getUserById(user.getId()).getBalance();
    }

    public UsersArrayList getUserList() {
        return userList;
    }

    public Object[] getTransfers(User user) {
        return user.getTransactionsList().TransformIntoArray();
    }

    public TransactionsLinkedList getTransactionsList() {
        return transactionsList;
    }

    public void addUser(User user) {
        userList.addUser(user);
    }

    public void performTransferTransaction(Integer recipientId, Integer senderId, Integer amountToTransfer) throws UserNotFoundException, IllegalTransactionException {
        UUID transactionId = UUID.randomUUID();
        User recipient = userList.getUserById(recipientId);
        checkUser(recipient);
        User sender = userList.getUserById(senderId);
        checkUser(sender);

        if (sender.getBalance() < amountToTransfer) throw new IllegalTransactionException("Amount exceeds balance");

        Transaction transaction1 = new Transaction(transactionId, recipient, sender, Transaction.Category.DEBIT, amountToTransfer);
        Transaction transaction2 = new Transaction(transactionId, sender, recipient, Transaction.Category.CREDIT, -amountToTransfer);

        recipient.getTransactionsList().addTransaction(transaction1);
        sender.getTransactionsList().addTransaction(transaction2);

        recipient.setBalance(recipient.getBalance() + amountToTransfer);
        sender.setBalance(sender.getBalance() - amountToTransfer);

        transactionsList.addTransaction(transaction1);
        transactionsList.addTransaction(transaction2);
    }

    public void removeTransactionById(UUID transactionId, Integer userId) {
        userList.getUserById(userId).getTransactionsList().removeTransactionById(transactionId);
    }

    public Object[] checkTransactions() {
        TransactionsLinkedList unpairedTransactionsList = new TransactionsLinkedList();

        for (int i = 0; i < transactionsList.getSize(); ++i) {
            boolean isDuplicate = false;
            for (int j = 0; j < transactionsList.getSize(); ++j) {
                if (i != j && transactionsList.getTransaction(i).getId().equals(transactionsList.getTransaction(j).getId())) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                unpairedTransactionsList.addTransaction(transactionsList.getTransaction(i));
            }
        }

        return unpairedTransactionsList.toArray();
    }

    private void checkUser(User user) {
        if (user == null) throw new UserNotFoundException("User not found");
    }
}