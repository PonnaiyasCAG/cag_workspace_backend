package net.workspace.workspace_backend.model;

public class FileUploadResponse {
    private String status;
    private String message;

    public FileUploadResponse() {
    }

    public FileUploadResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
