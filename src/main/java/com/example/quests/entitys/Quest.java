package com.example.quests.entitys;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "quests")
public class Quest extends BaseEntityId {
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
    private Organizer organizer;
    private Set<Booking> booking;
    private boolean deleted;


    public Quest(String photoUrl, String name, String description, String location, int duration, int maxParticipants, double price, int difficulty, int ageRestriction, String genre, Organizer organizer) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.description = description;
        this.location = location;
        this.duration = duration;
        this.maxParticipants = maxParticipants;
        this.price = price;
        this.difficulty = difficulty;
        this.rating = 0;
        this.ageRestriction = ageRestriction;
        this.genre = genre;
        this.organizer = organizer;
        this.deleted = false;
    }

    protected Quest() {}

    @Column(name = "photo_url", length = 200)
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "location", nullable = false, length = 200)
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Column(name = "max_participants", nullable = false)
    public int getMaxParticipants() {
        return maxParticipants;
    }
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    @Column(name = "price", nullable = false)
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "difficulty", nullable = false)
    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Column(name = "rating")
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Column(name = "age_restriction")
    public int getAgeRestriction() {
        return ageRestriction;
    }
    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @OneToMany(mappedBy = "quest", fetch = FetchType.LAZY)
    public Set<Booking> getBooking() {
        return booking;
    }
    public void setBooking(Set<Booking> booking) {
        this.booking = booking;
    }

    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
