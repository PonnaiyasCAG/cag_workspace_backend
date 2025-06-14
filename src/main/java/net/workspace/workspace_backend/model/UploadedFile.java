package net.workspace.workspace_backend.model;

import jakarta.persistence.*;

@Entity
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String userId;

    @Lob
    private byte[] base64;  // Make sure this is byte[] and not String

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getBase64() {
        return base64;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }
}
