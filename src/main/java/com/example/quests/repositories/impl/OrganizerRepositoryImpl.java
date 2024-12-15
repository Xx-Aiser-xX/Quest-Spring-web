package com.example.quests.repositories.impl;

import com.example.quests.entitys.Organizer;
import com.example.quests.repositories.OrganizerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizerRepositoryImpl extends BaseRepository<Organizer> implements OrganizerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Organizer personAndOrganizer(String email){
        try {
            return entityManager.createQuery(
                            "SELECT o FROM Organizer o " +
                                    "JOIN FETCH o.person p " +
                                    "WHERE p.email = :email", Organizer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public Organizer organizerPhone(String phone){
        try {
            return entityManager.createQuery(
                            "SELECT o FROM Organizer o " +
                                    "WHERE o.phone = :phone", Organizer.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
