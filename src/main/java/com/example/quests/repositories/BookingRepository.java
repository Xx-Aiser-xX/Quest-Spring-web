package com.example.quests.repositories;

import com.example.quests.entitys.Booking;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingRepository {
    List<Booking> getAll(Class<Booking> entityClass, boolean deleted);
    Page<Booking> getEntitys(Class<Booking> entityClass, int page, int size, boolean deleted);
    void create(Booking entity);
    Booking findById(Class<Booking> entityClass, int id);
    void update(Booking entity);

    List<Booking> bookingsQuest(int id, boolean deleted);
    List<Booking> bookingsAndReviewsQuest(int id, boolean deleted);
    List<Booking> userBookings(int id, boolean deleted);
    Booking bookingAndQuest(int id);
}
