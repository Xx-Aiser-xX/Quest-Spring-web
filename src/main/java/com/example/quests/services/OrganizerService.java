package com.example.quests.services;

import com.example.quests.dto.OrganizerDto;
import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.PersonOrganizerDto;
import org.springframework.data.domain.Page;

public interface OrganizerService {
    void create(OrganizerRegistrationDto ord);
    void update(OrganizerDto ord);
    Page<OrganizerDto> getAll(int page, int size);
    OrganizerDto findById(int id);
    void deleteById(int id);

    PersonOrganizerDto findByOrganizerAndEmail(String email);
}
