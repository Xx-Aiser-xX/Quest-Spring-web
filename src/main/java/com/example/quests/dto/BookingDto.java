package com.example.quests.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDto implements Serializable {
    private int id;
    private int participants;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private int questId;
    private int userId;

    public BookingDto(int participants, LocalDate date, LocalTime time, String status, int questId, int userId) {
        this.participants = participants;
        this.date = date;
        this.time = time;
        this.status = status;
        this.questId = questId;
        this.userId = userId;
    }

    public BookingDto() {
    }

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
}
