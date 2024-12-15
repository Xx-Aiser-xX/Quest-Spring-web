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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminUserControllerImpl implements AdminUserController {
    private final UserService userService;
    private final AdminMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    public AdminUserControllerImpl(UserService userService, AdminMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/users")
    public String pageAdminUsers(@ModelAttribute("form") PageSearchForm form,
                                 Principal principal, Model model) {
        LOG.log(Level.INFO, "the user creation page for the administrator, ADMIN: " + principal.getName());
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
    public String pageCreateUser(Principal principal, Model model) {
        LOG.log(Level.INFO, "page creating a user from the administrator, ADMIN: " + principal.getName());
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
    public String createUser(@Valid @ModelAttribute("form") UserRegistrationForm userForm,
                                  BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "the user create page for the administrator, userEmail:" + userForm.email() + ", ADMIN: " + principal.getName());
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
    public String editForm(@PathVariable int id, Principal principal, Model model) {
        LOG.log(Level.INFO, "the user update page for the administrator, userId:" + id + ", ADMIN: " + principal.getName());
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
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") UpdateUserFrom form,
                       BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "changing the user from the administrator, userId:" + id + ", ADMIN: " + principal.getName());
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
    public String deletedQuest(@PathVariable int id) {
        LOG.log(Level.INFO, "deleting a user by an administrator, userId:" + id);
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
