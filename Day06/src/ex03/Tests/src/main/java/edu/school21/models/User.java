package edu.school21.models;

public class User {
    private long id;
    private String login;
    private String password;
    private boolean authenticationSuccessStatus;
//    (true — authenticated, false — not authenticated).

    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authenticationSuccessStatus = false;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticationSuccessStatus() {
        return authenticationSuccessStatus;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthenticationSuccessStatus(boolean authenticationSuccessStatus) {
        this.authenticationSuccessStatus = authenticationSuccessStatus;
    }
}
