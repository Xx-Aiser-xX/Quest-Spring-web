package com.example.quests.services.impl;

import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.UserRegistrationDto;
import com.example.quests.entitys.Organizer;
import com.example.quests.entitys.Person;
import com.example.quests.entitys.User;
import com.example.quests.entitys.enums.UserRoles;
import com.example.quests.exceptions.MailOrPhoneAlreadyExistsException;
import com.example.quests.repositories.OrganizerRepository;
import com.example.quests.repositories.PersonRepository;
import com.example.quests.repositories.UserRepository;
import com.example.quests.services.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, OrganizerRepository organizerRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "'email-' + #regDto.email")
    public void register(UserRegistrationDto regDto){
        if(!regDto.getPassword().equals(regDto.getConfirmPassword())){
            throw new RuntimeException("passwords.match");
        }

        Person p = personRepository.findByEmail(regDto.getEmail());
        if(p != null){
            throw new MailOrPhoneAlreadyExistsException(regDto.getEmail());
        }
        User u = userRepository.userPhone(regDto.getPhone());
        if(u != null){
            throw new MailOrPhoneAlreadyExistsException(regDto.getPhone());
        }

        Person person = new Person(regDto.getEmail(), passwordEncoder.encode(
                regDto.getPassword()), UserRoles.USER);
        personRepository.create(person);
        User user = new User(regDto.getName(),
                regDto.getPhone(), regDto.getPhotoUrl(), person);

        userRepository.create(user);
    }

    @Override
    @Transactional
    public void registerOrganizer(OrganizerRegistrationDto regDto){
        if(!regDto.getPassword().equals(regDto.getConfirmPassword())){
            throw new RuntimeException("passwords.match");
        }

        Person p = personRepository.findByEmail(regDto.getEmail());

        if(p != null){
            throw new MailOrPhoneAlreadyExistsException(regDto.getEmail());
        }
        Organizer o = organizerRepository.organizerPhone(regDto.getPhone());
        if(o != null){
            throw new MailOrPhoneAlreadyExistsException(regDto.getPhone());
        }
        Person person = new Person(regDto.getEmail(), passwordEncoder.encode(
                regDto.getPassword()), UserRoles.ORGANIZER);
        personRepository.create(person);
        Organizer organizer = new Organizer(regDto.getName(), regDto.getPhone(),
                regDto.getCity(), regDto.getDescription(), regDto.getPhotoUrl(), person);
        organizerRepository.create(organizer);
    }
}
