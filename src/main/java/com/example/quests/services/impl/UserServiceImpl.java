package com.example.quests.services.impl;

import com.example.quests.dto.*;
import com.example.quests.entitys.Person;
import com.example.quests.entitys.User;
import com.example.quests.entitys.enums.UserRoles;
import com.example.quests.repositories.PersonRepository;
import com.example.quests.repositories.UserRepository;
import com.example.quests.services.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PersonRepository personRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "'email-' + #userDto.email")
    public void create(UserRegistrationDto userDto) {
        if(userDto.getPassword().equals(userDto.getConfirmPassword())) {
            Person person = new Person(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), UserRoles.USER);
            personRepository.create(person);
            User user = new User(userDto.getName(), userDto.getPhone(), userDto.getPhotoUrl(), person);
            userRepository.create(user);
        }
    }

    @Override
    @Cacheable(value = "users")
    public Page<UserDto> getAll(int page, int size) {
        Page<User> users = userRepository.getEntitys(User.class, page - 1, size, false);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    @Cacheable(value = "users")
    public UserDto findById(int id) {
        User user = userRepository.findById(User.class, id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(UserDto ud) {
        User u = userRepository.findById(User.class, ud.getId());
        u.setName(ud.getName());
        u.setPhone(ud.getPhone());
        u.setCompletedQuests(ud.getCompletedQuests());
        u.setPhotoUrl(ud.getPhotoUrl());
        userRepository.update(u);
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void deleteById(int id) {
        User u = userRepository.findById(User.class, id);
        u.setDeleted(true);
        userRepository.update(u);
    }

    @Override
    @Cacheable(value = "users", key = "'page-' + #page + 'size' + #size")
    public Page<UserDto> honorBoard(int page, int size){
        List<User> users = userRepository.getAll(User.class, false);
        users.sort((u1, u2) -> Integer.compare(u2.getCompletedQuests(), u1.getCompletedQuests()));
        List<UserDto> usersDto = users.stream().map(u -> modelMapper.map(u, UserDto.class)).toList();
        return new PageImpl<>(usersDto, PageRequest.of(page - 1, size), usersDto.size());
    }

    @Override
    @Cacheable(value = "users", key = "'email-' + #email")
    public PersonUserDto findByEmail(String email){
        User user = userRepository.personAndUser(email);
        return user == null ? null : modelMapper.map(user, PersonUserDto.class);
    }

    @Override
    @Transactional
    public void updateCompletedQuestsUser(int id, int count){
        User user = userRepository.findById(User.class, id);
        user.setCompletedQuests(count);
        userRepository.update(user);
    }

    @Override
    @Cacheable(value = "users", key = "'userAndEmail-' + #email")
    public UserAndEmailDto findByUserAndEmail(String email){
        User user = userRepository.personAndUser(email);
        return modelMapper.map(user, UserAndEmailDto.class);
    }
}
