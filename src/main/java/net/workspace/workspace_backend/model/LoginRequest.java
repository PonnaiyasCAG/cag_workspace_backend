package net.workspace.workspace_backend.model;

public class LoginRequest {
    private String mailId;
    private String password;

    // Getters and setters
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
}

