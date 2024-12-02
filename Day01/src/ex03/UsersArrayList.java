public class UsersArrayList implements UsersList {
    private int index = 0, buffer = 10, count_users = 0;
    private User[] users = new User[buffer];

    @Override
    public void addUser(User user) throws UserNotFoundException {
        if (isDuplicateUser(user)) throw new UserNotFoundException("User already exist");

        if (index == buffer) {
            buffer *= 2;
            User[] newUsers = new User[buffer];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            users = newUsers;
        }

        users[index] = user;
        index++;
        count_users++;
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        boolean isExistingUser = false;

        for (int i = 0; i < users.length; ++i) {
            if (users[i].getId() == id) isExistingUser = true;
        }

        if (!isExistingUser) throw new UserNotFoundException("Non-existent identifier");

        return users[id];
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException {
        if (index > buffer) throw new UserNotFoundException("Non-existent index");

        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return count_users;
    }

    private boolean isDuplicateUser(User user) {
        for (int i = 0; i < users.length; ++i) {
            if (users[i] == user) return true;
        }

        return false;
    }
}
