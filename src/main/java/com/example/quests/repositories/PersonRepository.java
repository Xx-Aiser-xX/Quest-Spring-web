package com.example.quests.repositories;

import com.example.quests.entitys.Person;

public interface PersonRepository {
    Person findByEmail(String email);
    Person personAndUser(String email);
    void create(Person person);

}
