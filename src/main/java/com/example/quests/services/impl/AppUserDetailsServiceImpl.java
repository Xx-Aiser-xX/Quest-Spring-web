package com.example.quests.services.impl;

import com.example.quests.entitys.Person;
import com.example.quests.repositories.PersonRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public AppUserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person p = personRepository.findByEmail(username);
        if(p == null) {
            throw new UsernameNotFoundException(username + " was not found!");
        }
        UserDetails ud =  new User(
                p.getEmail(),
                p.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + p.getRole().name())));
        return ud;
    }
}
