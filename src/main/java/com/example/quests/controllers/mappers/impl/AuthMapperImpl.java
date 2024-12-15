package com.example.quests.controllers.mappers.impl;

import com.example.quests.controllers.mappers.AuthMapper;
import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.UserRegistrationDto;
import org.example.questcontracts.form.create.OrganizerRegistrationForm;
import org.example.questcontracts.form.create.UserRegistrationForm;
import org.springframework.stereotype.Component;

@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public UserRegistrationDto mapRegistrationFormToDto(UserRegistrationForm form){
        UserRegistrationDto regUser = new UserRegistrationDto(form.name(), form.email(),
                form.phone(), form.photoUrl(), form.password(), form.confirmPassword());
        return regUser;
    }

    @Override
    public OrganizerRegistrationDto mapRegistrationFormToDto(OrganizerRegistrationForm form){
        OrganizerRegistrationDto regOrganizer = new OrganizerRegistrationDto(form.name(), form.email(),
                form.phone(), form.city(), form.description(), form.password(), form.confirmPassword(), form.photoUrl());
        return regOrganizer;
    }
}
