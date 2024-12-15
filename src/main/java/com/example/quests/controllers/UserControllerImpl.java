package com.example.quests.controllers;

import com.example.quests.controllers.mappers.UserViewModelMapper;
import com.example.quests.dto.*;
import com.example.quests.services.BookingService;
import com.example.quests.services.ReviewService;
import com.example.quests.services.UserService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.UserController;
import org.example.questcontracts.form.UserSearchForm;
import org.example.questcontracts.form.create.CreateReviewForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final BookingService bookingService;
    private final ReviewService reviewService;
    private final UserViewModelMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public UserControllerImpl(UserService userService, BookingService bookingService, ReviewService reviewService, UserViewModelMapper mapper) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.reviewService = reviewService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    public String pageUser(@ModelAttribute("userForm") UserSearchForm userForm,
                           Principal principal, Model model) {
        LOG.log(Level.INFO, "Show user page");
        var bookingPage = userForm.userPage() != null ? userForm.userPage() : 1;
        var bookingSize = 4;
        String email = principal.getName();
        UserAndEmailDto userDto = userService.findByUserAndEmail(email);
        List<BookingAndQuestDto> currentBookingDto = bookingService.currentUserBookings(userDto.getId());
        Page<BookingAndQuestDto> pastBookingDto = bookingService.pastUserBookings(userDto.getId(), bookingPage, bookingSize);
        UserViewModel viewModel = mapper.mapUserViewModel(
                createBaseViewModel (principal, "Пользователь"),
                userDto,
                currentBookingDto,
                pastBookingDto);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", userForm);
        return "page-user";
    }

    @Override
    @PostMapping("/save")
    public String saveReview(@Valid @ModelAttribute CreateReviewForm reviewForm,
                             @RequestParam int bookingId,
                             RedirectAttributes redirectAttributes) {
        LOG.log(Level.INFO, "adding a review to a quest by a user, bookingId" + reviewForm.bookingId());
        ReviewDto bookingDto = mapper.mapReviewFormToDto(reviewForm);
        reviewService.create(bookingDto);
        redirectAttributes.addAttribute("id", reviewForm.questId());
        return "redirect:/quest/{id}";
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
