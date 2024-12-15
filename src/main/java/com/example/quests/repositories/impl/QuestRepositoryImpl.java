package com.example.quests.repositories.impl;

import com.example.quests.entitys.Quest;
import com.example.quests.repositories.QuestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestRepositoryImpl extends BaseRepository<Quest> implements QuestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Quest> questsFromTheOrganizer(int id, boolean deleted) {
        return entityManager.createQuery(
                "SELECT q FROM Quest q " +
                        "WHERE q.organizer.id = :id " +
                        "AND q.deleted = :isDeleted", Quest.class)
                .setParameter("id", id)
                .setParameter("isDeleted", deleted)
                .getResultList();
    }

    @Override
    public List<Quest> questAndBooking(boolean deleted){
        return entityManager.createQuery(
                "SELECT q FROM Quest q " +
                        "LEFT JOIN FETCH q.booking " +
                        "WHERE q.deleted = :isDeleted", Quest.class)
                .setParameter("isDeleted", deleted)
                .getResultList();
    }

    @Override
    public Quest questAndOrganizer(int id, boolean deleted){
        return entityManager.createQuery(
                "SELECT q FROM Quest q " +
                        "LEFT JOIN FETCH q.organizer " +
                        "WHERE q.id = :id " +
                        "AND q.deleted = :isDeleted", Quest.class)
                .setParameter("id", id)
                .setParameter("isDeleted", deleted)
                .getSingleResult();
    }

    @Override
    public Page<Quest> questsAndOrganizer(int page, int size, boolean deleted){
        long total = entityManager.createQuery(
                        "SELECT COUNT(q) FROM Quest q " +
                                "WHERE q.deleted = :isDeleted", Long.class)
                .setParameter("isDeleted", deleted)
                .getSingleResult();
        List<Quest> quests = entityManager.createQuery(
                        "SELECT q FROM Quest q " +
                                "LEFT JOIN FETCH q.organizer " +
                                "WHERE q.deleted = :isDeleted", Quest.class)
                .setParameter("isDeleted", deleted)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return new PageImpl<>(quests, PageRequest.of(page, size), total);
    }
}


