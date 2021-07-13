package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements EntityID<Integer>, Serializable {
    private int userID;
    private String user;
    private String password;
    private LocalDateTime firstLogin;

    User() {

    }

    public User(String user, String password, LocalDateTime firstLogin) {
        this.user = user;
        this.password = password;
        this.firstLogin = firstLogin;
    }

    public int getUserID() {
        return userID;
    }

    public LocalDateTime getFirstLogin() {
        return firstLogin;
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }

    public void setFirstLogin(LocalDateTime firstLogin) {
        this.firstLogin = firstLogin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "model.User{" +
                "ID= " + userID + '\'' +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", firstLogin=" + firstLogin +
                '}';
    }

    @Override
    public Integer getID() {
        return this.userID;
    }

    @Override
    public void setID(Integer integer) {
        this.userID = integer;
    }
}
