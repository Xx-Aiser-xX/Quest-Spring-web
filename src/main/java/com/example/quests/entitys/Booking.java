package com.example.quests.entitys;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntityId {
    private int participants;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private Quest quest;
    private User user;
    private Set<Review> review;
    private boolean deleted;

    public Booking(int participants, LocalDate date, LocalTime time, String status, Quest quest, User user) {
        this.participants = participants;
        this.date = date;
        this.time = time;
        this.status = status;
        this.quest = quest;
        this.user = user;
        this.deleted = false;
    }

    protected Booking() {}

    @Column(name = "participants", nullable = false)
    public int getParticipants() {
        return participants;
    }
    public void setParticipants(int participants) {
        this.participants = participants;
    }

    @Column(name = "date", nullable = false)
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "time", nullable = false)
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Column(name = "status", nullable = false, length = 15)
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    public Quest getQuest() {
        return quest;
    }
    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    public Set<Review> getReview() {
        return review;
    }
    public void setReview(Set<Review> review) {
        this.review = review;
    }

    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean delete) {
        this.deleted = delete;
    }
}
