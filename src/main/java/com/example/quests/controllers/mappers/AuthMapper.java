package com.example.quests.controllers.mappers;

import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.UserRegistrationDto;
import org.example.questcontracts.form.create.OrganizerRegistrationForm;
import org.example.questcontracts.form.create.UserRegistrationForm;

public interface AuthMapper {
    UserRegistrationDto mapRegistrationFormToDto(UserRegistrationForm form);
    OrganizerRegistrationDto mapRegistrationFormToDto(OrganizerRegistrationForm form);
}
