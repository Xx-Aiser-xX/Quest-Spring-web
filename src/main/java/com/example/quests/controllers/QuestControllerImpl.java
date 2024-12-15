package com.example.quests.controllers;

import com.example.quests.controllers.mappers.QuestViewModelMapper;
import com.example.quests.dto.*;
import com.example.quests.services.BookingService;
import com.example.quests.services.QuestService;
import com.example.quests.services.ReviewService;
import com.example.quests.services.UserService;
import org.example.questcontracts.controllers.QuestController;
import org.example.questcontracts.form.BookingSearchForm;
import org.example.questcontracts.form.ReviewSearchForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.QuestViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import java.security.Principal;

@Controller
@RequestMapping("/quest")
public class QuestControllerImpl implements QuestController {
    private final QuestService questService;
    private final BookingService bookingService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final QuestViewModelMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public QuestControllerImpl(QuestService questService, BookingService bookingService,
                               ReviewService reviewService, UserService userService, QuestViewModelMapper mapper) {
        this.questService = questService;
        this.bookingService = bookingService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/{id}")
    public String pageQuest(@ModelAttribute("bookingForm") BookingSearchForm bookingForm,
                            @ModelAttribute("reviewForm") ReviewSearchForm reviewForm,
                            @PathVariable int id, Principal principal, Model model){
        LOG.log(Level.INFO, "Show quest page");
        var bookingPage = bookingForm.bookingPage() != null ? bookingForm.bookingPage() : 1;
        var bookingSize = 10;
        var reviewPage = reviewForm.reviewPage() != null ? reviewForm.reviewPage() : 1;
        var reviewSize = 5;


        QuestAndOrganizerNameDto qe = questService.findById(id);
        Page<ReviewDto> reviewDto = reviewService.questReviews(id, reviewPage, reviewSize);
        Page<BookingDto> bk = bookingService.bookingAQuest(id, bookingPage, bookingSize);


        QuestViewModel viewModel = mapper.mapViewModel(
                createBaseViewModel(principal, "Квест"),
                qe,
                reviewDto,
                bk);

        model.addAttribute("model", viewModel);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("reviewForm", reviewForm);
        return "page-quest";
    }

    @Override
    @PostMapping("/reserve")
    public String booking(Principal principal, @RequestParam int bookingId){
        LOG.log(Level.INFO, "booking a quest by the user, bookingId: " + bookingId);
        String email = principal.getName();
        UserAndEmailDto userDto = userService.findByUserAndEmail(email);
        bookingService.booking(userDto.getId(), bookingId);
        return "redirect:../user";
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
