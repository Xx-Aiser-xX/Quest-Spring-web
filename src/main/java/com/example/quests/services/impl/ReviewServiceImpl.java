package com.example.quests.services.impl;

import com.example.quests.dto.ReviewDto;
import com.example.quests.entitys.Booking;
import com.example.quests.entitys.Review;
import com.example.quests.repositories.BookingRepository;
import com.example.quests.repositories.ReviewRepository;
import com.example.quests.services.QuestService;
import com.example.quests.services.ReviewService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final QuestService questService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, BookingRepository bookingRepository, QuestService questService, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.questService = questService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void create(ReviewDto reviewDto) {
        Booking booking = bookingRepository.bookingAndQuest(reviewDto.getBookingId());
        if (booking.isDeleted()) {
            throw new RuntimeException("booking deleted");
        }
        Review review = modelMapper.map(reviewDto, Review.class);
        questService.updateRatingQuest(booking.getQuest().getId(), review.getRating());
        reviewRepository.create(review);
    }

    @Override
    public Page<ReviewDto> getAll(int page, int size) {
        Page<Review> reviews = reviewRepository.getEntitys(Review.class, page - 1, size, false);
        return reviews.map(review -> modelMapper.map(review, ReviewDto.class));
    }

    @Override
    public ReviewDto findById(int id) {
        Review review = reviewRepository.findById(Review.class, id);
        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
    @Transactional
    public void update(ReviewDto reviewDto) {
        Booking booking = bookingRepository.findById(Booking.class, reviewDto.getBookingId());
        if (booking.isDeleted()) {
            throw new RuntimeException("booking deleted");
        }
        Review review = modelMapper.map(reviewDto, Review.class);
        review.setBooking(booking);
        reviewRepository.update(review);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Review r = reviewRepository.findById(Review.class, id);
        r.setDeleted(true);
        reviewRepository.update(r);
    }

    @Override
    public Page<ReviewDto> questReviews(int id, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Booking> bookings = bookingRepository.bookingsAndReviewsQuest(id, false);

        List<Review> rw = new ArrayList<>();
        for(Booking booking : bookings){
            rw.addAll(booking.getReview());
        }
        List<ReviewDto> reviewDto = rw.stream().map(rv -> modelMapper.map(rv, ReviewDto.class)).toList();
        List<ReviewDto> pages = reviewDto.subList((page - 1) * size, Math.min((page - 1) * size + size, reviewDto.size()));

        return new PageImpl<ReviewDto>(pages, pageable, reviewDto.size());
    }
}
