package com.example.quests.dto;

import java.io.Serializable;

public class OrganizerDto implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String city;
    private String description;
    private double rating;
    private String photoUrl;

    public OrganizerDto(String name, String phone, String city, String description, double rating, String photoUrl) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.description = description;
        this.rating = rating;
        this.photoUrl = photoUrl;
    }

    public OrganizerDto() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
