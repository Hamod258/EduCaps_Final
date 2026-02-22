package model;

import java.io.Serializable;

public class LearningCapsule implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private String mediaPath;
    private String category; 

    public LearningCapsule(int id, String title, String description, String mediaPath, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mediaPath = mediaPath;
        this.category = category;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMediaPath() { return mediaPath; }
    public void setMediaPath(String mediaPath) { this.mediaPath = mediaPath; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nTitle: " + title +
               "\nCategory: " + category +
               "\nDescription: " + description +
               "\nMedia: " + mediaPath;
    }
}