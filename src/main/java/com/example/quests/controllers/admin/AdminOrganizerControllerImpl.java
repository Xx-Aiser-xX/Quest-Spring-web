package com.example.quests.controllers.admin;

import com.example.quests.controllers.mappers.AdminMapper;
import com.example.quests.dto.OrganizerDto;
import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.PersonUserDto;
import com.example.quests.services.OrganizerService;
import com.example.quests.services.UserService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.admin.AdminOrganizerController;
import org.example.questcontracts.form.PageSearchForm;
import org.example.questcontracts.form.create.OrganizerRegistrationForm;
import org.example.questcontracts.form.update.UpdateOrganizerForm;
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
public class AdminOrganizerControllerImpl implements AdminOrganizerController {
    private final OrganizerService organizerService;
    private final UserService userService;
    private final AdminMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    public AdminOrganizerControllerImpl(OrganizerService organizerService, UserService userService, AdminMapper mapper) {
        this.organizerService = organizerService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/organizers")
    public String pageAdminOrganizers(@ModelAttribute("form") PageSearchForm form,
                                      Principal principal, Model model) {
        LOG.log(Level.INFO, "organizers review page for admin, ADMIN: " + principal.getName());
        var page = form.page() != null ? form.page() : 1;
        var size = 5;
        Page<OrganizerDto> organizerDto = organizerService.getAll(page, size);

        AdminPanelOrganizerViewModel viewModel = mapper.mapOrganizerViewModel(
                createBaseViewModel(principal, "Админ"),
                organizerDto);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "admin/page-admin-organizers";
    }

    @Override
    @GetMapping("/create/organizer")
    public String pageCreateOrganizer(Principal principal, Model model) {
        LOG.log(Level.INFO, "the organizer creation page for the administrator, ADMIN: " + principal.getName());
        AdminPanelCreateOrganizerViewModel viewModel = new AdminPanelCreateOrganizerViewModel(
                createBaseViewModel(principal, "Создание бронирования")
        );
        OrganizerRegistrationForm bookingForm = new OrganizerRegistrationForm(null, null,
                null, null,null, null, null, null);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", bookingForm);
        return "admin/create-entity/page-admin-create-organizer";
    }

    @Override
    @PostMapping("/create/organizer")
    public String createOrganizer(@Valid @ModelAttribute("form") OrganizerRegistrationForm form,
                                BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "creating an organizer from the administrator, organizerEmail:" + form.email() + ", ADMIN: " + principal.getName());
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Ошибка: " + error.getDefaultMessage());
            });
            AdminPanelCreateOrganizerViewModel viewModel = new AdminPanelCreateOrganizerViewModel(
                    createBaseViewModel(principal, "Создание квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "admin/create-entity/page-admin-create-organizer";
        }
        OrganizerRegistrationDto organizerDto = mapper.mapOrganizerFormToDto(form);
        organizerService.create(organizerDto);
        return "redirect:../bookings";
    }

    @Override
    @GetMapping("/update/organizer/{id}")
    public String editForm(@PathVariable int id, Principal principal, Model model) {
        LOG.log(Level.INFO, "the organizer update page for the administrator, organizerId:" + id + " ADMIN: " + principal.getName());
        OrganizerDto o = organizerService.findById(id);
        AdminPanelUpdateOrganizerViewModel viewModel = new AdminPanelUpdateOrganizerViewModel(
                createBaseViewModel(principal, "Обновление квеста")
        );
        UpdateOrganizerForm form = mapper.mapOrganizerUpdateForm(o);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "admin/update-entity/page-admin-update-organizer";
    }

    @Override
    @PostMapping("/update/organizer/{id}")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") UpdateOrganizerForm form,
                       BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "changing the organizer from the administrator, organizerId:" + id + " , ADMIN: " + principal.getName());
        if (bindingResult.hasErrors()) {
            AdminPanelUpdateQuestViewModel viewModel = new AdminPanelUpdateQuestViewModel(
                    createBaseViewModel(principal, "Обновление квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "admin/update-entity/page-admin-update-organizer";
        }
        OrganizerDto organizerDto = mapper.mapOrganizerFormToDto(form);
        organizerService.update(organizerDto);
        return "redirect:../../../organizer/" + id;
    }

    @Override
    @PostMapping("/deleted/organizer/{id}")
    public String deletedQuest(@PathVariable int id) {
        LOG.log(Level.INFO, "deleting the organizer by the administrator, organizerId:" + id);
        organizerService.deleteById(id);
        return "redirect:../../organizers";
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