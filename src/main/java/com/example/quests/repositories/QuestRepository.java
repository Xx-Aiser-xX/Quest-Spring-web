package com.example.quests.repositories;

import com.example.quests.entitys.Quest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestRepository {
    Quest findById(Class<Quest> entityClass, int id);
    List<Quest> getAll(Class<Quest> entityClass, boolean deleted);
    Page<Quest> getEntitys(Class<Quest> entityClass, int page, int size, boolean deleted);
    void update(Quest quest);
    void create(Quest entity);

    List<Quest> questsFromTheOrganizer(int id, boolean deleted);
    List<Quest> questAndBooking(boolean deleted);
    Quest questAndOrganizer(int id, boolean deleted);
    Page<Quest> questsAndOrganizer(int page, int size, boolean deleted);
}
