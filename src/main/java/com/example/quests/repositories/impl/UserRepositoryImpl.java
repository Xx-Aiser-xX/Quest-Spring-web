package com.example.quests.repositories.impl;

import com.example.quests.entitys.User;
import com.example.quests.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User personAndUser(String email){
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u " +
                                    "JOIN FETCH u.person p " +
                                    "WHERE p.email = : email ", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User userPhone(String phone){
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u " +
                                    "WHERE u.phone = : phone ", User.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
