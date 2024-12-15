package com.example.quests.services;

import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.UserRegistrationDto;

public interface AuthService {
    void register(UserRegistrationDto regDto);
    void registerOrganizer(OrganizerRegistrationDto regDto);
}
