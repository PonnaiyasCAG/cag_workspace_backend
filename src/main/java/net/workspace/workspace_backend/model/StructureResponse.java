package net.workspace.workspace_backend.model;

import java.util.List;
import java.util.Map;

public class StructureResponse {
    private String status;
    private String message;
    private List<Map<String, Object>> receivedStructure;

    public StructureResponse() {}

    public StructureResponse(String status, String message, List<Map<String, Object>> receivedStructure) {
        this.status = status;
        this.message = message;
        this.receivedStructure = receivedStructure;
    }

    // Getters and setters
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

    public List<Map<String, Object>> getReceivedStructure() {
        return receivedStructure;
    }
    public void setReceivedStructure(List<Map<String, Object>> receivedStructure) {
        this.receivedStructure = receivedStructure;
    }
}
