package com.example.quests.entitys;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "organizers")
public class Organizer extends BaseEntityId {
    private String name;
    private String phone;
    private String city;
    private String description;
    private double rating;
    private String photoUrl;
    private Set<Quest> quest;
    private Person person;
    private boolean deleted;


    public Organizer(String name, String phone, String city, String description, String photoUrl, Person person) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.description = description;
        this.rating = 0;
        this.photoUrl = photoUrl;
        this.person = person;
        this.deleted = false;
    }

    protected Organizer() {}

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "phone", nullable = false, unique = true, length = 11)
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "city", nullable = false, length = 40)
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "rating")
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Column(name = "photo_url", length = 150)
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @OneToMany(mappedBy = "organizer", fetch = FetchType.LAZY)
    public Set<Quest> getQuest() {
        return quest;
    }
    public void setQuest(Set<Quest> quest) {
        this.quest = quest;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
