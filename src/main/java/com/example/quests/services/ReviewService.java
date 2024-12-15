package com.example.quests.services;

import com.example.quests.dto.ReviewDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ReviewService {
    void create(ReviewDto reviewDto);
    Page<ReviewDto> getAll(int page, int size);
    ReviewDto findById(int id);
    void update(ReviewDto reviewDto);
    void deleteById(int id);

    Page<ReviewDto> questReviews(int id, int page, int size);
}
