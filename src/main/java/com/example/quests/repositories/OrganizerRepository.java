package com.example.quests.repositories;

import com.example.quests.entitys.Organizer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizerRepository {
    Organizer findById(Class<Organizer> entityClass, int id);
    List<Organizer> getAll(Class<Organizer> entityClass, boolean deleted);
    Page<Organizer> getEntitys(Class<Organizer> entityClass, int page, int size, boolean deleted);
    void update(Organizer quest);
    void create(Organizer entity);

    Organizer personAndOrganizer(String email);
    Organizer organizerPhone(String phone);
}
