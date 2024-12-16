package com.example.quests.controllers.admin;

import com.example.quests.controllers.mappers.AdminMapper;
import com.example.quests.dto.PersonUserDto;
import com.example.quests.dto.QuestAndOrganizerNameDto;
import com.example.quests.services.QuestService;
import com.example.quests.services.UserService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.admin.AdminQuestController;
import org.example.questcontracts.form.PageSearchForm;
import org.example.questcontracts.form.create.CreateQuestForm;
import org.example.questcontracts.form.update.UpdateQuestForm;
import org.example.questcontracts.viewmodel.admin.AdminPanelCreateQuestViewModel;
import org.example.questcontracts.viewmodel.admin.AdminPanelQuestViewModel;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.admin.AdminPanelUpdateQuestViewModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminQuestControllerImpl implements AdminQuestController {
    private final QuestService questService;
    private final UserService userService;
    private final AdminMapper mapper;

    public AdminQuestControllerImpl(QuestService questService, UserService userService, AdminMapper mapper) {
        this.questService = questService;
        this.userService = userService;
        this.mapper = mapper;
    }


    @Override
    @GetMapping("/quests")
    public String pageAdminQuests(@ModelAttribute("form") PageSearchForm form,
                                  Principal principal, Model model) {
        var page = form.page() != null ? form.page() : 1;
        var size = 5;
        Page<QuestAndOrganizerNameDto> questDto = questService.getQuestAndNameOrganizer(page, size);

        AdminPanelQuestViewModel viewModel = mapper.mapQuestViewModel(
                createBaseViewModel(principal, "Админ"),
                questDto);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "admin/page-admin-quests";
    }

    @Override
    @GetMapping("/create/quest")
    public String pageAdminCreateQuest(Principal principal, Model model) {
        AdminPanelCreateQuestViewModel viewModel = new AdminPanelCreateQuestViewModel(
                createBaseViewModel(principal,"Создание квеста")
        );

        CreateQuestForm questForm = new CreateQuestForm(null, 0,null,
                    0, null, null, null, 0,
                    0, 0,0);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", questForm);
        return "admin/create-entity/page-admin-create-quest";
    }

    @Override
    @PostMapping("/create/quest")
    public String adminCreateQuest(@Valid @ModelAttribute("form") CreateQuestForm questForm,
                              BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            AdminPanelCreateQuestViewModel viewModel = new AdminPanelCreateQuestViewModel(
                    createBaseViewModel(principal,"Создание квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", questForm);
            return "admin/create-entity/page-admin-create-quest";
        }

        QuestAndOrganizerNameDto questDto = mapper.mapQuestFormToDto(questForm);
        questService.create(questDto);
        return "redirect:../quests";
    }

    @Override
    @GetMapping("/update/quest/{id}")
    public String pageAdminEditQuest(@PathVariable int id, Principal principal, Model model) {
        QuestAndOrganizerNameDto q = questService.findById(id);
        AdminPanelUpdateQuestViewModel viewModel = new AdminPanelUpdateQuestViewModel(
                createBaseViewModel(principal,"Обновление квеста")
        );
        UpdateQuestForm questForm = mapper.questUpdateForm(q);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", questForm);
        return "admin/update-entity/page-admin-update-quest";
    }

    @Override
    @PostMapping("/update/quest/{id}")
    public String adminEditQuest(@PathVariable int id, @Valid @ModelAttribute("form") UpdateQuestForm form,
                       BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            AdminPanelUpdateQuestViewModel viewModel = new AdminPanelUpdateQuestViewModel(
                    createBaseViewModel(principal,"Обновление квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "admin/update-entity/page-admin-update-quest";
        }
        QuestAndOrganizerNameDto questDto = mapper.mapQuestFormToDto(form);

        questService.update(questDto);
        return "redirect:../../../quest/" + id;
    }

    @Override
    @PostMapping("/deleted/quest/{id}")
    public String adminDeletedQuest(@PathVariable int id) {
        questService.deleteById(id);
        return "redirect:../../quests";
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
