package com.example.quests.dto;

import java.io.Serializable;

public class UserAndEmailDto implements Serializable {
    private int id;
    private String photoUrl;
    private String name;
    private String personEmail;
    private String phone;
    private int completedQuests;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPersonEmail() {
        return personEmail;
    }
    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCompletedQuests() {
        return completedQuests;
    }
    public void setCompletedQuests(int completedQuests) {
        this.completedQuests = completedQuests;
    }

}
