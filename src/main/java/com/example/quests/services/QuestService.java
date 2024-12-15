package com.example.quests.services;

import com.example.quests.dto.QuestDto;
import com.example.quests.dto.QuestAndOrganizerNameDto;
import org.springframework.data.domain.Page;

public interface QuestService {
    void create(QuestAndOrganizerNameDto questDto);
    void update(QuestAndOrganizerNameDto dto);
    Page<QuestDto> getAll(int page, int size);
    QuestAndOrganizerNameDto findById(int id);
    void deleteById(int id);

    void updateRatingQuest(int id, int rating);
    Page<QuestDto> questsFromTheOrganizer(int id, int page, int size);
    Page<QuestAndOrganizerNameDto> getQuestAndNameOrganizer(int page, int size);
    Page<QuestDto> getQuests(int page, int size, String filter, String search);
}
