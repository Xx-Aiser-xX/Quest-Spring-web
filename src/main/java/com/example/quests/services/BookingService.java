package com.example.quests.services;

import com.example.quests.dto.BookingAndQuestDto;
import com.example.quests.dto.BookingDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {
    void create(BookingDto bookingDto);
    Page<BookingDto> getAll(int page, int size);
    BookingDto findById(int id);
    void update(BookingDto bookingDto);
    void deleteById(int id);

    Page<BookingDto> bookingAQuest(int id, int page, int size);
    Page<BookingDto> organizerBookingAQuest(String email, int id, int page, int size);
    List<BookingAndQuestDto> currentUserBookings(int id);
    Page<BookingAndQuestDto> pastUserBookings(int id, int page, int size);
    void booking(int userId, int bookingId);
    void creatingAReservationFromTheOrganizer(BookingDto bd, String email);
    void updateAReservationFromTheOrganizer(BookingDto bd, String email);
    void deletedAReservationFromTheOrganizer(int id, int questId, String email);
}
