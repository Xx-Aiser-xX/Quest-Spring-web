package com.example.quests.dto;

import java.io.Serializable;

public class PersonOrganizerDto implements Serializable {
    private int organizerId;
    private String photoUrl;
    private String email;

    public int getOrganizerId() {
        return organizerId;
    }
    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
