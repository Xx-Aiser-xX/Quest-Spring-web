package com.example.quests.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class BaseRepository<Entity> {

    @PersistenceContext
    protected EntityManager entityManager;

    public void create(Entity entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Entity> getAll(Class<Entity> entityClass, boolean deleted) {
        try {
            return entityManager.createQuery("FROM " + entityClass.getName() + " e " +
                            "WHERE e.deleted = :isDeleted", entityClass)
                    .setParameter("isDeleted", deleted)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page<Entity> getEntitys(Class<Entity> entityClass, int page, int size, boolean deleted) {
        try {
            long total = entityManager.createQuery(
                    "SELECT COUNT(e) FROM " + entityClass.getName() + " e " +
                            "WHERE e.deleted = :isDeleted", Long.class)
                    .setParameter("isDeleted", deleted)
                    .getSingleResult();

            List<Entity> entitiys = entityManager.createQuery(
                    "FROM " + entityClass.getName() + " e " +
                            "WHERE e.deleted = :isDeleted", entityClass)
                    .setParameter("isDeleted", deleted)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();

            return new PageImpl<>(entitiys, PageRequest.of(page, size), total);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Entity findById(Class<Entity> entityClass, int id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Entity entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
