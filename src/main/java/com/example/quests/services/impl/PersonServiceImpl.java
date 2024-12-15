package com.example.quests.services.impl;

import com.example.quests.dto.PersonUserDto;
import com.example.quests.entitys.Person;
import com.example.quests.repositories.PersonRepository;
import com.example.quests.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonUserDto findByEmail(String email){
        Person person = personRepository.personAndUser(email);
        return modelMapper.map(person, PersonUserDto.class);
    }
}
