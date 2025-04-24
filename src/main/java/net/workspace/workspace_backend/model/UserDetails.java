package net.workspace.workspace_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generate userId

    private int userId;
    private String userName;
    private String mailId;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails(int userId, String userName, String mailId, String password) {
        this.userId = userId;
        this.userName = userName;
        this.mailId = mailId;
        this.password = password;
    }

    public UserDetails() {
    }
}



