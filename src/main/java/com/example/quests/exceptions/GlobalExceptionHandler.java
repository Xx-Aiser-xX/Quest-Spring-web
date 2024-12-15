package com.example.quests.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MailOrPhoneAlreadyExistsException.class)
    public String exception(MailOrPhoneAlreadyExistsException e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "exception";
    }
}
