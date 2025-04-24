package net.workspace.workspace_backend.model;

public class UserResponse {
    private String status;
    private UserDetails userDetails;

    public UserResponse(String status, UserDetails userDetails) {
        this.status = status;
        this.userDetails = userDetails;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}

