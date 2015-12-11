package com.example.teamnullpointer.ridesharenp;


public class PostDatabase {
    private String description;
    private String IDEmail;

    public PostDatabase(String description, String IDEmail) {
        this.setDescription(description);
        this.setIDEmail(IDEmail);
    }

    public String getDescription() {
        return description;
    }

    public String getIDEmail(){
        return IDEmail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIDEmail(String IDEmail){
        this.IDEmail = IDEmail;
    }
}
