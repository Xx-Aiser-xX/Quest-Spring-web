package com.example.quests.controllers;

import com.example.quests.controllers.mappers.MainViewModelMapper;
import com.example.quests.dto.PersonUserDto;
import com.example.quests.dto.QuestDto;
import com.example.quests.entitys.Person;
import com.example.quests.services.PersonService;
import com.example.quests.services.QuestService;
import com.example.quests.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.example.questcontracts.controllers.MainController;
import org.example.questcontracts.form.QuestSearchForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.MainViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/main")
public class MainControllerImpl implements MainController {
    private final QuestService questService;
    private final UserService userService;
    private final MainViewModelMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public MainControllerImpl(QuestService questService, UserService userService, MainViewModelMapper mapper) {
        this.questService = questService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    public String pageMain(@ModelAttribute("form") QuestSearchForm form,
                           @RequestParam(required = false, defaultValue = "no") String sort,
                           @RequestParam(required = false) String search,
                           Principal principal, Model model) {
        LOG.log(Level.INFO, "Show main page");
        var questPage = form.questPage() != null ? form.questPage() : 1;
        var questSize = 8;

        Page<QuestDto> questDto = questService.getQuests(questPage, questSize, sort, search);

        MainViewModel viewModel = mapper.mapViewModel(
                createBaseViewModel (principal, "Главная страница"),
                questDto);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        return "index";
    }

    @Override
    public BaseViewModel createBaseViewModel(Principal principal, String title) {
        String photoUrl = "";
        if (principal != null) {
            String email = principal.getName();
            PersonUserDto p = userService.findByEmail(email);
            if (p != null)
                photoUrl = p.getPhotoUrl();
        }
        return new BaseViewModel(title, photoUrl);
    }
}
