package com.example.quests.repositories;

import com.example.quests.entitys.Quest;
import com.example.quests.entitys.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewRepository {
    Review findById(Class<Review> entityClass, int id);
    List<Review> getAll(Class<Review> entityClass, boolean deleted);
    Page<Review> getEntitys(Class<Review> entityClass, int page, int size, boolean deleted);
    void update(Review quest);
    void create(Review entity);

    List<Review> getAllReviewsQuest(int id);
}
