package com.example.teamnullpointer.ridesharenp;


public class PostDatabase {
    private String description;

    public PostDatabase(String description) {
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
