package com.example.quests.entitys;

import com.example.quests.entitys.enums.UserRoles;
import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person extends BaseEntityId {
    private String email;
    private String password;
    private UserRoles role;
    private User user;
    private Organizer organizer;

    public Person(String email, String password, UserRoles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    protected Person() {
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "role", nullable = false)
    @Enumerated
    public UserRoles getRole() {
        return role;
    }
    public void setRole(UserRoles role) {
        this.role = role;
    }

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }
}

