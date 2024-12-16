package com.example.quests.controllers.admin;

import com.example.quests.controllers.mappers.AdminMapper;
import com.example.quests.dto.PersonUserDto;
import com.example.quests.dto.UserDto;
import com.example.quests.dto.UserRegistrationDto;
import com.example.quests.services.UserService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.admin.AdminUserController;
import org.example.questcontracts.form.PageSearchForm;
import org.example.questcontracts.form.create.UserRegistrationForm;
import org.example.questcontracts.form.update.UpdateUserFrom;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.admin.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminUserControllerImpl implements AdminUserController {
    private final UserService userService;
    private final AdminMapper mapper;


    public AdminUserControllerImpl(UserService userService, AdminMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/users")
    public String pageAdminUsers(@ModelAttribute("form") PageSearchForm form,
                                 Principal principal, Model model) {
        var page = form.page() != null ? form.page() : 1;
        var size = 5;
        Page<UserDto> userDto = userService.getAll(page, size);

        AdminPanelUserViewModel viewModel = mapper.mapUserViewModel(
                createBaseViewModel(principal,"Админ"),
                userDto);


        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "admin/page-admin-users";
    }

    @Override
    @GetMapping("/create/user")
    public String pageAdminCreateUser(Principal principal, Model model) {
        AdminPanelCreateUserViewModel viewModel = new AdminPanelCreateUserViewModel(
                createBaseViewModel(principal,"Создание бронирования")
        );
        UserRegistrationForm reviewForm = new UserRegistrationForm(null, null, null, null,null, null);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", reviewForm);
        return "admin/create-entity/page-admin-create-user";
    }

    @Override
    @PostMapping("/create/user")
    public String adminCreateUser(@Valid @ModelAttribute("form") UserRegistrationForm userForm,
                                  BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {

            AdminPanelCreateUserViewModel viewModel = new AdminPanelCreateUserViewModel(
                    createBaseViewModel(principal,"Создание квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", userForm);
            return "admin/create-entity/page-admin-create-user";
        }

        UserRegistrationDto userDto = mapper.userCreateForm(userForm);
        userService.create(userDto);
        return "redirect:../users";
    }

    @Override
    @GetMapping("/update/user/{id}")
    public String pageAdminEditUser(@PathVariable int id, Principal principal, Model model) {
        UserDto u = userService.findById(id);
        AdminPanelUpdateUserViewModel viewModel = new AdminPanelUpdateUserViewModel(
                createBaseViewModel(principal,"Обновление пользователя")
        );
        UpdateUserFrom organizerForm = mapper.organizerUpdateForm(u);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", organizerForm);
        return "admin/update-entity/page-admin-update-user";
    }

    @Override
    @PostMapping("/update/user/{id}")
    public String adminEditUser(@PathVariable int id, @Valid @ModelAttribute("form") UpdateUserFrom form,
                       BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            AdminPanelUpdateUserViewModel viewModel = new AdminPanelUpdateUserViewModel(
                    createBaseViewModel(principal,"Обновление квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "admin/update-entity/page-admin-update-user";
        }

        UserDto userDto = mapper.mapUserFormToDto(form);

        userService.update(userDto);
        return "redirect:../../users";
    }

    @Override
    @PostMapping("/deleted/user/{id}")
    public String adminDeletedQuest(@PathVariable int id) {
        userService.deleteById(id);
        return "redirect:../../users";
    }

    @Override
    public BaseViewModel createBaseViewModel(Principal principal, String title) {
        String photoUrl = "";
        if (principal != null) {
            String email = principal.getName();
            PersonUserDto p = userService.findByEmail(email);
            photoUrl = p.getPhotoUrl();
        }
        return new BaseViewModel(title, photoUrl);
    }
}
