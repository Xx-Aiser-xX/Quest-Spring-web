package com.example.quests.services.impl;

import com.example.quests.dto.BookingAndQuestDto;
import com.example.quests.dto.BookingDto;
import com.example.quests.entitys.Booking;
import com.example.quests.entitys.Organizer;
import com.example.quests.entitys.Quest;
import com.example.quests.entitys.User;
import com.example.quests.repositories.OrganizerRepository;
import com.example.quests.repositories.QuestRepository;
import com.example.quests.repositories.UserRepository;
import com.example.quests.services.BookingService;
import com.example.quests.services.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.quests.repositories.BookingRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableCaching
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, QuestRepository questRepository, UserRepository userRepository, OrganizerRepository organizerRepository, UserService userService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.questRepository = questRepository;
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    @CacheEvict(value = "bookings",allEntries = true)
    public void create(BookingDto bd) {
        Quest quest = questRepository.findById(Quest.class, bd.getQuestId());
        User user = userRepository.findById(User.class, bd.getUserId());
        if(!quest.isDeleted()) {
            Booking booking = modelMapper.map(bd, Booking.class);
            booking.setQuest(quest);
            booking.setUser(user);
            bookingRepository.create(booking);
        }
        else {
            throw new RuntimeException("quest deleted");
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "bookings",allEntries = true)
    public void creatingAReservationFromTheOrganizer(BookingDto bd, String email){
        boolean exist = doesTheQuestBelongToTheOrganizer(email, bd.getQuestId());
        if(!exist){
            throw new RuntimeException("access is blocked");
        }
        Quest quest = questRepository.findById(Quest.class, bd.getQuestId());
        User user = userRepository.findById(User.class, bd.getUserId());
        if(!quest.isDeleted()) {
            Booking booking = modelMapper.map(bd, Booking.class);
            booking.setQuest(quest);
            booking.setUser(user);
            bookingRepository.create(booking);
        }
        else {
            throw new RuntimeException("quest deleted");
        }
    }

    @Override
    @Cacheable(value = "bookings")
    public Page<BookingDto> getAll(int page, int size) {
        Page<Booking> bookings = bookingRepository.getEntitys(Booking.class, page - 1, size, false);
        return bookings.map(booking -> modelMapper.map(booking, BookingDto.class));
    }

    @Override
    @Cacheable(value = "bookings", key = "'id-' + #id")
    public BookingDto findById(int id) {
        Booking booking = bookingRepository.findById(Booking.class, id);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "bookings",allEntries = true)
    public void update(BookingDto bookingDto) {
        Booking booking = modelMapper.map(bookingDto, Booking.class);
        Quest quest = questRepository.findById(Quest.class, bookingDto.getQuestId());
        User user = userRepository.findById(User.class, bookingDto.getUserId());
        if(user.isDeleted() || quest.isDeleted()) {
            throw new RuntimeException("quest or user deleted");
        }
        booking.setQuest(quest);
        booking.setUser(user);
        bookingRepository.update(booking);
    }

    @Override
    @Transactional
    @CacheEvict(value = "bookings",allEntries = true)
    public void updateAReservationFromTheOrganizer(BookingDto bd, String email){
        boolean exist = doesTheQuestBelongToTheOrganizer(email, bd.getQuestId());
        if(!exist){
            throw new RuntimeException("access is blocked");
        }
        Booking booking = modelMapper.map(bd, Booking.class);
        Quest quest = questRepository.findById(Quest.class, bd.getQuestId());
        User user = userRepository.findById(User.class, bd.getUserId());
        if(user.isDeleted() || quest.isDeleted()) {
            throw new RuntimeException("quest or user deleted");
        }
        booking.setQuest(quest);
        booking.setUser(user);
        bookingRepository.update(booking);
    }

    @Override
    @Transactional
    @CacheEvict(value = "bookings",allEntries = true)
    public void deleteById(int id) {
        Booking b = bookingRepository.findById(Booking.class, id);
        b.setDeleted(true);
        bookingRepository.update(b);
    }

    @Override
    @Transactional
    @CacheEvict(value = "bookings",allEntries = true)
    public void deletedAReservationFromTheOrganizer(int id, int questId, String email){
        boolean exist = doesTheQuestBelongToTheOrganizer(email, questId);
        if(!exist){
            throw new RuntimeException("access is blocked");
        }
        Booking b = bookingRepository.findById(Booking.class, id);
        b.setDeleted(true);
        bookingRepository.update(b);
    }

    @Override
    @Cacheable(value = "bookings", key = "'bookingAQuest-' + #id")
    public Page<BookingDto> bookingAQuest(int id, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Booking> bookings = bookingRepository.bookingsQuest(id, false);

        List<Booking> futureBookings = new ArrayList<Booking>();
        for (Booking booking : bookings){
            LocalDateTime ldt = booking.getDate().atTime(booking.getTime());
            if(ldt.isAfter(LocalDateTime.now()))
                futureBookings.add(booking);
        }
        List<BookingDto> bookingDto = futureBookings.stream().map(bk -> modelMapper.map(bk, BookingDto.class)).toList();
        List<BookingDto> pages = bookingDto.subList((page - 1) * size, Math.min((page - 1) * size + size, bookingDto.size()));
        return new PageImpl<BookingDto>(pages, pageable, bookingDto.size());
    }

    private boolean doesTheQuestBelongToTheOrganizer(String email, int id){
        Organizer organizer = organizerRepository.personAndOrganizer(email);
        List<Quest> quests = questRepository.questsFromTheOrganizer(organizer.getId(),false);
        for(Quest quest : quests){
            if(quest.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    @Cacheable(value = "bookings")
    public Page<BookingDto> organizerBookingAQuest(String email, int id, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Booking> bookings = bookingRepository.bookingsQuest(id, false);

        boolean exist = doesTheQuestBelongToTheOrganizer(email, id);

        if(!exist){
            throw new RuntimeException("access is blocked");
        }
        List<Booking> futureBookings = new ArrayList<Booking>();

        for (Booking booking : bookings) {
            LocalDateTime ldt = booking.getDate().atTime(booking.getTime());
            if (ldt.isAfter(LocalDateTime.now()))
                futureBookings.add(booking);
        }
        List<BookingDto> bookingDto = futureBookings.stream().map(bk -> modelMapper.map(bk, BookingDto.class)).toList();
        List<BookingDto> pages = bookingDto.subList((page - 1) * size, Math.min((page - 1) * size + size, bookingDto.size()));
        return new PageImpl<BookingDto>(pages, pageable, bookingDto.size());
    }

    @Override
    @Cacheable(value = "bookings")
    public List<BookingAndQuestDto> currentUserBookings(int id){
        List<Booking> bookings = bookingRepository.userBookings(id, false);
        List<Booking> currentBookings = new ArrayList<>();
        for(Booking booking : bookings){
            LocalDateTime dateTime = booking.getDate().atTime(booking.getTime());
            if(dateTime.compareTo(LocalDateTime.now()) >= 0 ) {
                currentBookings.add(booking);
            }
        }

        return currentBookings.stream().map(bk -> modelMapper.map(bk, BookingAndQuestDto.class)).toList();
    }

    @Override
    @Cacheable(value = "bookings")
    public Page<BookingAndQuestDto> pastUserBookings(int id, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Booking> bookings = bookingRepository.userBookings(id, false);
        List<Booking> pastUserBookings = new ArrayList<>();
        for(Booking booking : bookings){
            LocalDateTime dateTime = booking.getDate().atTime(booking.getTime());
            if(dateTime.compareTo(LocalDateTime.now()) < 0 )
                pastUserBookings.add(booking);
        }
        userService.updateCompletedQuestsUser(id, pastUserBookings.size());
        List<BookingAndQuestDto> bookingDto = pastUserBookings.stream().map(bk -> modelMapper.map(bk, BookingAndQuestDto.class)).toList();
        List<BookingAndQuestDto> pages = bookingDto.subList((page - 1) * size, Math.min((page - 1) * size + size, bookingDto.size()));
        return new PageImpl<BookingAndQuestDto>(pages, pageable, bookingDto.size());
    }

    @Override
    @Transactional
    @CacheEvict(value = "bookings",allEntries = true)
    public void booking(int userId, int bookingId){
        Booking booking = bookingRepository.findById(Booking.class, bookingId);
        if(booking.getStatus().equals("свободно")) {
            User user = userRepository.findById(User.class, userId);
            booking.setUser(user);
            booking.setStatus("забронировано");
            bookingRepository.update(booking);
        }
        else {
            throw new RuntimeException("booking is busy");
        }
    }
}
