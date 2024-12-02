public class Program {
    public static void main(String[] args) {
        User user1 = new User("John", 300);
        User user2 = new User("Mike", 200);
        User user3 = new User("Ivan", 100);
        System.out.println(user1.getName() + " (id: " + user1.getId() + ", balance: " + user1.getBalance() + ")");
        System.out.println(user2.getName() + " (id: " + user2.getId() + ", balance: " + user2.getBalance() + ")");
        System.out.println(user3.getName() + " (id: " + user3.getId() + ", balance: " + user3.getBalance() + ")");
    }
}