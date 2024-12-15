package com.example.quests.controllers.admin;

import com.example.quests.controllers.mappers.AdminMapper;
import com.example.quests.dto.BookingDto;
import com.example.quests.dto.PersonUserDto;
import com.example.quests.services.BookingService;
import com.example.quests.services.UserService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.admin.AdminBookingController;
import org.example.questcontracts.form.PageSearchForm;
import org.example.questcontracts.form.create.CreateBookingForm;
import org.example.questcontracts.form.update.UpdateBookingForm;
import org.example.questcontracts.viewmodel.admin.*;
import org.example.questcontracts.viewmodel.BaseViewModel;
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
@RequestMapping("/admin")
public class AdminBookingControllerImpl implements AdminBookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final AdminMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public AdminBookingControllerImpl(BookingService bookingService, UserService userService, AdminMapper mapper) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/bookings")
    public String pageAdminBookings(@ModelAttribute("form") PageSearchForm form,
                                    Principal principal, Model model) {
        LOG.log(Level.INFO, "booking review page for admin, ADMIN: " + principal.getName());
        var page = form.page() != null ? form.page() : 1;
        var size = 5;
        Page<BookingDto> bookingDto = bookingService.getAll(page, size);

        AdminPanelBookingViewModel viewModel = mapper.mapBookingViewModel(
                createBaseViewModel(principal,"Админ"),
                bookingDto);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "admin/page-admin-bookings";
    }

    @Override
    @GetMapping("/create/booking")
    public String pageCreateBooking(Principal principal, Model model) {
        LOG.log(Level.INFO, "the booking creation page for the administrator, ADMIN: " + principal.getName());
        AdminPanelCreateBookingViewModel viewModel = new AdminPanelCreateBookingViewModel(
                createBaseViewModel(principal, "Создание бронирования")
        );
        CreateBookingForm bookingForm = new CreateBookingForm(0, null, null, null,0,0);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", bookingForm);
        return "admin/create-entity/page-admin-create-booking";
    }

    @Override
    @PostMapping("/create/booking")
    public String createBooking(@Valid @ModelAttribute("form") CreateBookingForm bookingForm,
                                BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "creating a reservation from the administrator, userId: " + bookingForm.userId());
        if (bindingResult.hasErrors()) {
            AdminPanelCreateBookingViewModel viewModel = new AdminPanelCreateBookingViewModel(
                    createBaseViewModel(principal, "Создание Бронирования")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", bookingForm);
            return "admin/create-entity/page-admin-create-booking";
        }
        BookingDto bookingDto = mapper.mapBookingFormToDto(bookingForm);
        bookingService.create(bookingDto);
        return "redirect:../bookings";
    }

    @Override
    @GetMapping("/update/booking/{id}")
    public String editForm(@PathVariable int id, Principal principal, Model model) {
        LOG.log(Level.INFO, "the booking update page for the administrator, bookingId: " + id + ", ADMIN: " + principal.getName());
        BookingDto b = bookingService.findById(id);
        AdminPanelUpdateBookingViewModel viewModel = new AdminPanelUpdateBookingViewModel(
                createBaseViewModel(principal, "Обновление бронирования")
        );
        UpdateBookingForm bookingForm = mapper.mapBookingUpdateForm(b);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", bookingForm);
        return "admin/update-entity/page-admin-update-booking";
    }

    @Override
    @PostMapping("/update/booking/{id}")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") UpdateBookingForm form,
                       BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "changing the reservation from the administrator, bookingId: " + id + ", ADMIN: " + principal.getName());
        if (bindingResult.hasErrors()) {
            System.out.println(form);
            AdminPanelUpdateBookingViewModel viewModel = new AdminPanelUpdateBookingViewModel(
                    createBaseViewModel(principal, "Обновление бронирования")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "admin/update-entity/page-admin-update-booking";
        }
        BookingDto bookingDto = mapper.mapBookingFormToDto(form);
        bookingService.update(bookingDto);
        return "redirect:../../bookings";
    }

    @Override
    @PostMapping("/deleted/booking/{id}")
    public String deletedBooking(@PathVariable int id) {
        LOG.log(Level.INFO, "deleting a reservation by the administrator, bookingId: " + id);
        bookingService.deleteById(id);
        return "redirect:../../bookings";
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
