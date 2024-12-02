public class Program {
    public static void main(String[] args) {
        User user1 = new User("John", 300);
        User user2 = new User("Mike", 200);
        User user3 = new User("Leo", 100);
        User user4 = new User("John", 300);
        User user5 = new User("Mike", 200);
        User user6 = new User("Leo", 100);
        User user7 = new User("John", 300);
        User user8 = new User("Mike", 200);
        User user9 = new User("Leo", 100);
        User user10 = new User("John", 300);
        User user11 = new User("Mike", 200);
        User user12 = new User("Leo", 100);
        UsersArrayList usersArrayList = new UsersArrayList();

        usersArrayList.addUser(user1);
        usersArrayList.addUser(user2);
        usersArrayList.addUser(user3);
        usersArrayList.addUser(user4);
        usersArrayList.addUser(user5);
        usersArrayList.addUser(user6);
        usersArrayList.addUser(user7);
        usersArrayList.addUser(user8);
        usersArrayList.addUser(user9);
        usersArrayList.addUser(user10);
        usersArrayList.addUser(user11);
        usersArrayList.addUser(user12);

        for (int i = 0; i < usersArrayList.getNumberOfUsers(); ++i) {
            System.out.println(usersArrayList.getUserByIndex(i).getName() + " (id: " + usersArrayList.getUserByIndex(i).getId() + ", balance: " + usersArrayList.getUserByIndex(i).getBalance() + ")");
        }
    }
}
