package com.example.quests.repositories;

import com.example.quests.entitys.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRepository {
    User findById(Class<User> entityClass, int id);
    List<User> getAll(Class<User> entityClass, boolean deleted);
    Page<User> getEntitys(Class<User> entityClass, int page, int size, boolean deleted);
    void update(User quest);
    void create(User entity);

    User personAndUser(String email);
    User userPhone(String phone);
}
