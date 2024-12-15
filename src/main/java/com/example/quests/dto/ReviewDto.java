package com.example.quests.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ReviewDto implements Serializable {
    private int id;
    private int rating;
    private String reviewText;
    private LocalDate reviewDate;
    private String name;
    private int bookingId;

    public ReviewDto(int rating, String reviewText, String name, int bookingId) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = LocalDate.now();
        this.name = name;
        this.bookingId = bookingId;
    }

    public ReviewDto() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
