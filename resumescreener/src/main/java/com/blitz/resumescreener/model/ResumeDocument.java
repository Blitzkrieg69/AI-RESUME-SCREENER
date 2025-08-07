package com.blitz.resumescreener.model;

import jakarta.persistence.*;

@Entity
public class ResumeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private int score;

    @Lob // Specifies that this should be stored as a Large Object (for file data)
    @Column(length = 10000000) // Set a max size for the file, e.g., 10MB
    private byte[] data;

    @ManyToOne // A User can have many documents
    @JoinColumn(name = "user_id") // Creates a user_id column in this table
    private User uploader;

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public User getUploader() { return uploader; }
    public void setUploader(User uploader) { this.uploader = uploader; }
}