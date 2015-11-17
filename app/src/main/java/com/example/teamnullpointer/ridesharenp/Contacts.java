package com.example.teamnullpointer.ridesharenp;

/**
 * Created by eric on 11/15/15.
 */
public class Contacts {
    private String description;

    public Contacts(String description) {
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
