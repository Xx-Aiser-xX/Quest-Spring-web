package com.example.quests.controllers;

import com.example.quests.controllers.mappers.HonorBoardViewModelMapper;
import com.example.quests.dto.PersonUserDto;
import com.example.quests.dto.UserDto;
import com.example.quests.services.UserService;
import org.example.questcontracts.controllers.HonorBoardController;
import org.example.questcontracts.form.PageSearchForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.HonorBoardViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/honor-boards")
public class HonorBoardControllerImpl implements HonorBoardController {
    private final UserService userService;
    private final HonorBoardViewModelMapper mapper;

    @Autowired
    public HonorBoardControllerImpl(UserService userService, HonorBoardViewModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    public String pageHonorBoardUser(@ModelAttribute("form") PageSearchForm form,
                                     Principal principal, Model model){

        var page = form.page() != null ? form.page() : 1;
        var size = 9;

        Page<UserDto> usersDto = userService.honorBoard(page, size);

        HonorBoardViewModel viewModel = mapper.mapViewModel(
                createBaseViewModel (principal, "Доска почёта"),
                usersDto);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "page-honor-board";
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
