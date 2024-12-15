package com.example.quests.services;

import com.example.quests.dto.PersonUserDto;
import com.example.quests.dto.UserAndEmailDto;
import com.example.quests.dto.UserDto;
import com.example.quests.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;

public interface UserService {
    void create(UserRegistrationDto userDto);
    void update(UserDto ud);
    Page<UserDto> getAll(int page, int size);
    UserDto findById(int id);
    void deleteById(int id);

    Page<UserDto> honorBoard(int page, int size);
    PersonUserDto findByEmail(String email);
    UserAndEmailDto findByUserAndEmail(String email);
    void updateCompletedQuestsUser(int id, int count);
}
