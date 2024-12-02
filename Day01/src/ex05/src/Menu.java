import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    TransactionsService transactionsService;
    private Scanner scanner;

    public Menu() {
        transactionsService = new TransactionsService();
    }

    public void menu() {
        while (true) {
            drawMenu();
            if (input() == 1) break;
        }
    }

    public void drawMenu() {
        System.out.println(
                "1. Add a user\n" +
                        "2. View user balances\n" +
                        "3. Perform a transfer\n" +
                        "4. View all transactions for a specific user\n" +
                        "5. DEV – remove a transfer by ID\n" +
                        "6. DEV – check transfer validity\n" +
                        "7. Finish execution");
    }

    private int input() {
        scanner = new Scanner(System.in);
        int choose = 0;
        try {
            choose = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine(); // Считываем и игнорируем оставшийся перенос строки
            System.out.println("---------------------------------------------------------");
            return 0;
        }
        switch (choose) {
            case 1:
                addUser();
                System.out.println("---------------------------------------------------------");
                break;
            case 2:
                getUserBalance();
                System.out.println("---------------------------------------------------------");
                break;
            case 3:
                performTransfer();
                System.out.println("---------------------------------------------------------");
                break;
            case 4:
                getTransactionsUser();
                System.out.println("---------------------------------------------------------");
                break;
            case 5:
                removeTransferByID();
                System.out.println("---------------------------------------------------------");
                break;
            case 6:
                checkTransferValidity();
                System.out.println("---------------------------------------------------------");
                break;
            case 7:
                return 1;
        }

        return 0;
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        String str = scanner.nextLine();
        String[] strSplit = str.split(" ");

        if (!isCorrectInput(strSplit.length, 2)) return;

        User user = new User(strSplit[0], Integer.parseInt(strSplit[1]));
        transactionsService.addUser(user);
        System.out.println("User with id = " + user.getId() + " is added");
    }

    private void getUserBalance() {
        System.out.println("Enter a user ID");
        int id = scanner.nextInt();

        try {
            System.out.println(transactionsService.getUserList().getUserById(id).getName() + " - " + transactionsService.getUserList().getUserById(id).getBalance());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String str = scanner.nextLine();
        String[] strSplit = str.split(" ");

        if (!isCorrectInput(strSplit.length, 3)) return;

        try {
            transactionsService.performTransferTransaction(Integer.parseInt(strSplit[1]), Integer.parseInt(strSplit[0]), Integer.parseInt(strSplit[2]));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IllegalTransactionException e) {
            System.out.println("Transaction error: " + e.getMessage());
            return;
        }

        System.out.println("The transfer is completed");
    }

    private void getTransactionsUser() {
        System.out.println("Enter a user ID");
        int id = scanner.nextInt();
//        To Mike(id = 2) -100 with id = cc128842-2e5c-4cca-a44c-7829f53fc31f
        try {
            for (int i = 0; i < transactionsService.getUserList().getUserById(id).getTransactionsList().getSize(); ++i) {
                String senderName = transactionsService.getUserList().getUserById(id).getTransactionsList().getTransaction(i).getSender().getName();
                int transferAmount = transactionsService.getUserList().getUserById(id).getTransactionsList().getTransaction(i).getTransferAmount();
                int userId = transactionsService.getUserList().getUserById(id).getTransactionsList().getTransaction(i).getSender().getId();
                UUID transactionId = transactionsService.getUserList().getUserById(id).getTransactionsList().getTransaction(i).getId();
                System.out.println("To " + senderName + "(id = " + userId + ") " + transferAmount + " with id = " + transactionId);
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void removeTransferByID() {
        System.out.println("Enter a user ID and a transfer ID");
        String str = scanner.nextLine();
        String[] strSplit = str.split(" ");

        if (!isCorrectInput(strSplit.length, 2)) return;

        try {
            transactionsService.getUserList().getUserById(Integer.parseInt(strSplit[0]));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        int userId = (Integer.parseInt(strSplit[0]));
        User user = transactionsService.getUserList().getUserById(userId);
        int transferAmount = 0;

        for (int i = 0; i < user.getTransactionsList().getSize(); ++i) {
            if (user.getTransactionsList().getTransaction(i) == null) {
                System.out.println("Non-existent transaction");
                return;
            }

            if (user.getTransactionsList().getTransaction(i).getSender().getTransactionsList().getTransaction(i).getId().equals(UUID.fromString(strSplit[1]))) {
                transferAmount = user.getTransactionsList().getTransaction(i).getSender().getTransactionsList().getTransaction(i).getTransferAmount();
                break;
            }
        }
        transactionsService.removeTransactionById(UUID.fromString(strSplit[1]), Integer.parseInt(strSplit[0]));

        String name = "";
        int senderId = 0;
        for (int i = 0; i < user.getTransactionsList().getSize(); ++i) {
            name = user.getTransactionsList().getTransaction(i).getSender().getName();
            senderId = user.getTransactionsList().getTransaction(i).getSender().getId();
        }
        System.out.println("Transfer To " + name + "(id = " + senderId + ") " + transferAmount + " removed");
    }

    private void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] transactions = transactionsService.checkTransactions();
        String senderName = "", recepientName = "";
        int senderId = 0, recepientId = 0, transferAmount = 0;
        UUID transactionId = null;
        for (int i = 0; i < transactions.length; ++i) {
            senderName = transactions[i].getSender().getName();
            recepientName = transactions[i].getRecipient().getName();
            senderId = transactions[i].getSender().getId();
            recepientId = transactions[i].getRecipient().getId();
            transactionId = transactions[i].getId();
            transferAmount = transactions[i].getTransferAmount();
            System.out.println(recepientName + "(id = " + recepientId + ") has an unacknowledged transfer id = " +
                    transactionId + " from " + senderName + "(id = " + senderId + ") for " +
                    transferAmount);
        }
    }


    private boolean isCorrectInput(int length, int correctLength) {
        if (length != correctLength) {
            System.out.println("Invalid input.");
            return false;
        }

        return true;
    }
}