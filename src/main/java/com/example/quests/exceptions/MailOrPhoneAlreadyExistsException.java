package com.example.quests.exceptions;

public class MailOrPhoneAlreadyExistsException extends RuntimeException{
    public MailOrPhoneAlreadyExistsException(String message){
        super("Пользователь с почтой или телефоном " + message + " уже существует");
    }
}
