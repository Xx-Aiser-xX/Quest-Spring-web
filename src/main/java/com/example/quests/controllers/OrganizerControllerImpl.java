package com.example.quests.controllers;

import com.example.quests.controllers.mappers.OrganizerViewModelMapper;
import com.example.quests.dto.*;
import com.example.quests.services.BookingService;
import com.example.quests.services.OrganizerService;
import com.example.quests.services.QuestService;
import com.example.quests.services.UserService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.OrganizerController;
import org.example.questcontracts.form.PageSearchForm;
import org.example.questcontracts.form.QuestSearchForm;
import org.example.questcontracts.form.create.CreateBookingForm;
import org.example.questcontracts.form.create.OrganizerCreateQuestForm;
import org.example.questcontracts.form.create.OrganizerUpdateQuestForm;
import org.example.questcontracts.form.update.UpdateBookingForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.OrganizerPanelBookingViewModel;
import org.example.questcontracts.viewmodel.OrganizerViewModel;
import org.example.questcontracts.viewmodel.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/organizer")
public class OrganizerControllerImpl implements OrganizerController {
    private final OrganizerService organizerService;
    private final UserService userService;
    private final QuestService questService;
    private final BookingService bookingService;
    private final OrganizerViewModelMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public OrganizerControllerImpl(OrganizerService organizerService, UserService userService, QuestService questService, BookingService bookingService, OrganizerViewModelMapper mapper) {
        this.organizerService = organizerService;
        this.userService = userService;
        this.questService = questService;
        this.bookingService = bookingService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/{id}")
    public String pageOrganizer(@PathVariable int id,
                                @ModelAttribute("form") QuestSearchForm form,
                                Principal principal, Model model){
        LOG.log(Level.INFO, "Show organizer page");
        var questPage = form.questPage() != null ? form.questPage() : 1;
        var questSize = 5;

        OrganizerDto org = organizerService.findById(id);
        Page<QuestDto> questDto = questService.questsFromTheOrganizer(id, questPage, questSize);

        OrganizerViewModel viewModel = mapper.mapViewModel(
                createBaseViewModel(principal, "Организатор"),
                org,
                questDto);


        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "page-organizer";
    }

    @Override
    @GetMapping("/quests")
    public String pageOrganizerQuests(@ModelAttribute("form") PageSearchForm form,
                                Principal principal, Model model){
        LOG.log(Level.INFO, "Show the organizer quests page, organizerId: " + principal.getName());
        var questPage = form.page() != null ? form.page() : 1;
        var questSize = 5;

        String email = principal.getName();
        PersonOrganizerDto personOrganizerDto = organizerService.findByOrganizerAndEmail(email);
        Page<QuestDto> questDto = questService.questsFromTheOrganizer(personOrganizerDto.getOrganizerId(), questPage, questSize);

        AdminPanelQuestViewModel viewModel = mapper.organizerQuestsViewModel(
                createBaseViewModel(principal, "Квесты организатора"),
                questDto,
                personOrganizerDto
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "page-quests";
    }


    @Override
    @GetMapping("/create/quest")
    public String pageCreateQuest(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show the organizer create quest page, organizerId: " + principal.getName());

        AdminPanelCreateQuestViewModel viewModel = new AdminPanelCreateQuestViewModel(
                createBaseViewModel(principal,"Создание квеста")
        );

        OrganizerCreateQuestForm questForm = new OrganizerCreateQuestForm(null, 0,null,
                null, null, null, 0, 0, 0,0);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", questForm);
        return "page-quests-create";
    }

    @Override
    @PostMapping("/create/quest")
    public String createQuest(@Valid @ModelAttribute("form") OrganizerCreateQuestForm form,
                              BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "creating a quest from the organizer, organizerId: " + principal.getName());
        if (bindingResult.hasErrors()) {
            AdminPanelCreateQuestViewModel viewModel = new AdminPanelCreateQuestViewModel(
                    createBaseViewModel(principal,"Создание квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "page-quests-create";
        }
        String email = principal.getName();
        PersonOrganizerDto p = organizerService.findByOrganizerAndEmail(email);
        QuestAndOrganizerNameDto dto = mapper.mapQuestFormToDto(form, p.getOrganizerId());
        questService.create(dto);
        return "redirect:../quests";
    }

    @Override
    @GetMapping("/update/quest/{id}")
    public String editForm(@PathVariable int id, Principal principal, Model model) {
        LOG.log(Level.INFO, "Show the organizer update quest page, questId: " + id);
        QuestAndOrganizerNameDto q = questService.findById(id);
        AdminPanelUpdateQuestViewModel viewModel = new AdminPanelUpdateQuestViewModel(
                createBaseViewModel(principal,"Обновление квеста")
        );
        OrganizerUpdateQuestForm questForm = mapper.mapQuestForm(q);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", questForm);
        return "page-quests-update";
    }

    @Override
    @PostMapping("/update/quest/{id}")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") OrganizerUpdateQuestForm form,
                       BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "quest update from the organizer, questId: " + id);
        if (bindingResult.hasErrors()) {
            AdminPanelUpdateQuestViewModel viewModel = new AdminPanelUpdateQuestViewModel(
                    createBaseViewModel(principal,"Обновление квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "page-quests-update";
        }
        String email = principal.getName();
        PersonOrganizerDto p = organizerService.findByOrganizerAndEmail(email);
        QuestAndOrganizerNameDto dto = mapper.mapQuestFormToDto(form, p.getOrganizerId());
        questService.update(dto);
        return "redirect:../../quests";
    }

    @Override
    @PostMapping("/deleted/quest/{id}")
    public String deletedQuest(@PathVariable int id) {
        LOG.log(Level.INFO, "quest deleted from the organizer, questId: " + id);
        questService.deleteById(id);
        return "redirect:../../quests";
    }

    @Override
    @GetMapping("/bookings")
    public String pageOrganizerBookings(@ModelAttribute("form") PageSearchForm form,
                                    @RequestParam int questId,
                                    Principal principal, Model model) {
        LOG.log(Level.INFO, "the organizer quest booking page, organizerId" + principal.getName());
        var page = form.page() != null ? form.page() : 1;
        var size = 5;
        String email = principal.getName();
        Page<BookingDto> bookingDto = bookingService.organizerBookingAQuest(email, questId, page, size);

        OrganizerPanelBookingViewModel viewModel = mapper.mapBookingViewModel(
                createBaseViewModel(principal,"Организатор"),
                bookingDto,
                questId);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "page-bookings";
    }

    @Override
    @GetMapping("/create/booking/{questId}")
    public String pageCreateBooking(Principal principal,
                                    @PathVariable int questId,
                                    Model model) {
        LOG.log(Level.INFO, "the page for creating bookings for the organizer quest, organizerId" + principal.getName());

        AdminPanelCreateBookingViewModel viewModel = new AdminPanelCreateBookingViewModel(
                createBaseViewModel(principal, "Создание бронирования")
        );

        CreateBookingForm bookingForm = new CreateBookingForm(0, null, null, null,questId,0);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", bookingForm);
        return "page-bookings-create";
    }

    @Override
    @PostMapping("/create/booking")
    public String createBooking(@Valid @ModelAttribute("form") CreateBookingForm bookingForm,
                                @RequestParam int questId,
                                BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "creating bookings for the organizer quest, questId" + bookingForm.questId());
        if (bindingResult.hasErrors()) {
            AdminPanelCreateBookingViewModel viewModel = new AdminPanelCreateBookingViewModel(
                    createBaseViewModel(principal, "Создание Бронирования")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", bookingForm);
            return "page-bookings-create";
        }
        BookingDto bookingDto = mapper.mapBookingFormToDto(bookingForm, questId);
        bookingService.creatingAReservationFromTheOrganizer(bookingDto, principal.getName());
        return "redirect:../quests";
    }

    @Override
    @GetMapping("/update/booking/{id}")
    public String editBookingForm(@PathVariable int id, Principal principal, Model model) {
        LOG.log(Level.INFO, "the page for update bookings for the organizer quest, bookingId" + id);
        BookingDto b = bookingService.findById(id);
        AdminPanelUpdateBookingViewModel viewModel = new AdminPanelUpdateBookingViewModel(
                createBaseViewModel(principal, "Обновление бронирования")
        );
        UpdateBookingForm bookingForm = mapper.mapBookingUpdateForm(b);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", bookingForm);
        return "page-booking-update";
    }

    @Override
    @PostMapping("/update/booking/{id}")
    public String editBooking(@PathVariable int id,
                              @RequestParam int questId,
                              @Valid @ModelAttribute("form") UpdateBookingForm form,
                       BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "update bookings for the organizer's quest, bookingId" + id);
        if (bindingResult.hasErrors()) {
            AdminPanelUpdateBookingViewModel viewModel = new AdminPanelUpdateBookingViewModel(
                    createBaseViewModel(principal, "Обновление бронирования")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "page-booking-update";
        }
        BookingDto bookingDto = mapper.mapBookingFormToDto(form, questId);
        bookingService.updateAReservationFromTheOrganizer(bookingDto, principal.getName());
        return "redirect:../../quests";
    }

    @Override
    @PostMapping("/deleted/booking/{id}")
    public String deletedBooking(@PathVariable int id,
                                 @RequestParam int questId,
                                 Principal principal) {
        LOG.log(Level.INFO, "deleted bookings for the organizer's quest, bookingId" + id);
        bookingService.deletedAReservationFromTheOrganizer(id, questId, principal.getName());
        return "redirect:../../quests";
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