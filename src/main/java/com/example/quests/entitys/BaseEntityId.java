package com.example.quests.entitys;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntityId {
    private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false  )
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
