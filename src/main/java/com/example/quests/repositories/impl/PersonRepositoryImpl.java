package com.example.quests.repositories.impl;

import com.example.quests.entitys.Person;
import com.example.quests.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepositoryImpl  extends BaseRepository<Person> implements PersonRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Person findByEmail(String email){
        try {
            return entityManager.createQuery(
                            "SELECT p FROM Person p " +
                                    "WHERE p.email = : email ", Person.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Person personAndUser(String email){
        try {
            return entityManager.createQuery(
                            "SELECT p FROM Person p " +
                                    "JOIN FETCH p.user u " +
                                    "WHERE p.email = : email ", Person.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
