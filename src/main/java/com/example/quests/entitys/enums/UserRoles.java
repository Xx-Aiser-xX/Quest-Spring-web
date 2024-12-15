package com.example.quests.entitys.enums;

public enum UserRoles {
    USER(1), ORGANIZER(2), ADMIN(3);

    private int value;

    UserRoles(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
