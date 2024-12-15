package com.example.quests.entitys;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntityId {
    private String name;
    private String phone;
    private int completedQuests;
    private String photoUrl;
    private Set<Booking> booking;
    private Person person;
    private boolean deleted;

    public User(String name, String phone, String photoUrl, Person person) {
        this.name = name;
        this.phone = phone;
        this.completedQuests = 0;
        this.photoUrl = photoUrl;
        this.person = person;
        this.deleted = false;
    }

    protected User() {}

    @Column(name = "name", nullable = false, length = 30)
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

    @Column(name = "completed_quests")
    public int getCompletedQuests() {
        return completedQuests;
    }
    public void setCompletedQuests(int completedQuests) {
        this.completedQuests = completedQuests;
    }

    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Booking> getBooking() {
        return booking;
    }
    public void setBooking(Set<Booking> booking) {
        this.booking = booking;
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
