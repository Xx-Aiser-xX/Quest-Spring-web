package com.example.quests.repositories.impl;

import com.example.quests.entitys.Review;
import com.example.quests.repositories.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl extends BaseRepository<Review> implements ReviewRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Review> getAllReviewsQuest(int id){
        return entityManager.createQuery(
                "SELECT r FROM Review r " +
                        "JOIN r.booking b " +
                        "JOIN b.quest q " +
                        "WHERE q.id = :id", Review.class)
                .setParameter("id", id)
                .getResultList();
    }

}
