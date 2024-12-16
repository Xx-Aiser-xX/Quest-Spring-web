package com.example.quests.controllers;

import com.example.quests.controllers.mappers.AuthMapper;
import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.UserRegistrationDto;
import com.example.quests.services.AuthService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.AuthController;
import org.example.questcontracts.form.create.OrganizerRegistrationForm;
import org.example.questcontracts.form.create.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final AuthMapper mapper;

    @Autowired
    public AuthControllerImpl(AuthService authService, AuthMapper mapper) {
        this.authService = authService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/register")
    public String register(@ModelAttribute("form") UserRegistrationForm form, Model model) {
        model.addAttribute("form", new UserRegistrationForm("", "", "", "", "", ""));
        return "register";
    }

    @Override
    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("form") UserRegistrationForm form,
                             BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            return "register";
        }
        UserRegistrationDto regUser = mapper.mapRegistrationFormToDto(form);
        authService.register(regUser);
        return "redirect:/users/login";
    }

    @Override
    @GetMapping("/register/organizer")
    public String registerOrganizer(@ModelAttribute("form") UserRegistrationForm form, Model model) {
        model.addAttribute("form", new OrganizerRegistrationForm("", "", "", "", "", "", "", ""));
        return "register-organizer";
    }

    @Override
    @PostMapping("/register/organizer")
    public String doRegisterOrganizer(@Valid @ModelAttribute("form") OrganizerRegistrationForm form,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            return "register-organizer";
        }
        OrganizerRegistrationDto regOrganizer = mapper.mapRegistrationFormToDto(form);
        this.authService.registerOrganizer(regOrganizer);

        return "redirect:/users/login";
    }

    @Override
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Override
    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }
}
