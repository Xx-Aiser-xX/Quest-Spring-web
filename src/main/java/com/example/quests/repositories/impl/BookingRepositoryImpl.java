package com.example.quests.repositories.impl;

import com.example.quests.entitys.Booking;
import com.example.quests.repositories.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepositoryImpl extends BaseRepository<Booking> implements BookingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Booking> bookingsQuest(int id, boolean deleted){
        return entityManager.createQuery(
                        "SELECT b FROM Booking b " +
                                "WHERE b.quest.id = :id " +
                                "AND b.deleted = :isDeleted", Booking.class)
                .setParameter("isDeleted", deleted)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Booking> bookingsAndReviewsQuest(int id, boolean deleted){
        return entityManager.createQuery(
                        "SELECT b FROM Booking b " +
                                "JOIN FETCH b.review r " +
                                "WHERE b.quest.id = :id " +
                                "AND b.deleted = :isDeleted", Booking.class)
                .setParameter("isDeleted", deleted)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Booking> userBookings(int id, boolean deleted){
        return entityManager.createQuery(
                        "SELECT b FROM Booking b " +
                                "JOIN FETCH b.quest " +
                                "WHERE b.user.id = :id " +
                                "AND b.deleted = :isDeleted", Booking.class)
                .setParameter("isDeleted", deleted)
                .setParameter("id", id).getResultList();
    }

    @Override
    public Booking bookingAndQuest(int id){
        return entityManager.createQuery(
                "SELECT b FROM Booking b " +
                        "JOIN FETCH b.quest q " +
                        "WHERE b.id = :id",Booking.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
