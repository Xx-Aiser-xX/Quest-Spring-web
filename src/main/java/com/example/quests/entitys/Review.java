package com.example.quests.entitys;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntityId {
    private String name;
    private int rating;
    private String reviewText;
    private LocalDate reviewDate;
    private Booking booking;
    private boolean deleted;

    public Review(String name, int rating, String reviewText, Booking booking) {
        this.name = name;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = LocalDate.now();
        this.booking = booking;
        this.deleted = false;
    }

    protected Review() {}

    @Column(name = "author", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "rating", nullable = false)
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Column(name = "review_text", nullable = false, length = 2000)
    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @Column(name = "review_date", nullable = false)
    public LocalDate getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
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
