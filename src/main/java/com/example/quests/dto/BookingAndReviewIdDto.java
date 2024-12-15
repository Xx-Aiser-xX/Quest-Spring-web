package com.example.quests.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingAndReviewIdDto {
    private int id;
    private int participants;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private int questId;
    private int userId;
    private int reviewId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getParticipants() {
        return participants;
    }
    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuestId() {
        return questId;
    }
    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReviewId() {
        return reviewId;
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}
