package com.example.quests.services;

import com.example.quests.dto.PersonUserDto;

public interface PersonService {
    PersonUserDto findByEmail(String email);
}
