public class User {
    private Integer id;
    private String name;
    private Integer balance;

    User (Integer id, String name, Integer balance) {
        this.id = id;
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

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
