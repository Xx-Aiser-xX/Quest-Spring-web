package com.example.quests.dto;

import java.io.Serializable;

public class QuestAndOrganizerNameDto implements Serializable {
    private int id;
    private String photoUrl;
    private String name;
    private String description;
    private String location;
    private int duration;
    private int maxParticipants;
    private double price;
    private int difficulty;
    private double rating;
    private int ageRestriction;
    private String genre;
    private String organizerName;
    private int organizerId;

    public QuestAndOrganizerNameDto(String photoUrl, String name, String description, String location, int duration, int maxParticipants, double price, int difficulty, int ageRestriction, String genre, int organizerId) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.description = description;
        this.location = location;
        this.duration = duration;
        this.maxParticipants = maxParticipants;
        this.price = price;
        this.difficulty = difficulty;
        this.ageRestriction = ageRestriction;
        this.genre = genre;
        this.organizerId = organizerId;
    }

    public QuestAndOrganizerNameDto() {
    }

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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }
    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOrganizerName() {
        return organizerName;
    }
    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public int getOrganizerId() {
        return organizerId;
    }
    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }
}
