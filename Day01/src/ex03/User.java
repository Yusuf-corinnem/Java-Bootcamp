public class User {
    private Integer id;
    private String name;
    private Integer balance;
    private TransactionsList transactionsList;

    User(String name, Integer balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0) System.exit(-1);
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
