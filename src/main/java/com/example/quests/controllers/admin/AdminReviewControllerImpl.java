package com.example.quests.controllers.admin;

import com.example.quests.controllers.mappers.AdminMapper;
import com.example.quests.dto.PersonUserDto;
import com.example.quests.dto.ReviewDto;
import com.example.quests.services.ReviewService;
import com.example.quests.services.UserService;
import jakarta.validation.Valid;
import org.example.questcontracts.controllers.admin.AdminReviewController;
import org.example.questcontracts.form.PageSearchForm;
import org.example.questcontracts.form.create.CreateAdminReviewForm;
import org.example.questcontracts.form.update.UpdateReviewForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
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
@RequestMapping("/admin")
public class AdminReviewControllerImpl implements AdminReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final AdminMapper mapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    @Autowired
    public AdminReviewControllerImpl(ReviewService reviewService, UserService userService, AdminMapper mapper) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/reviews")
    public String pageAdminReviews(@ModelAttribute("form") PageSearchForm form,
                                   Principal principal, Model model) {
        LOG.log(Level.INFO, "reviews review page for admin, ADMIN: " + principal.getName());
        var page = form.page() != null ? form.page() : 1;
        var size = 5;
        Page<ReviewDto> reviewDto = reviewService.getAll(page, size);

        AdminPanelReviewViewModel viewModel = mapper.mapReviewViewModel(
                createBaseViewModel(principal, "Админ"),
                reviewDto);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "admin/page-admin-reviews";
    }

    @Override
    @GetMapping("/create/review")
    public String pageCreateReview(Principal principal, Model model) {
        LOG.log(Level.INFO, "the review creation page for the administrator, ADMIN: " + principal.getName());
        AdminPanelCreateReviewViewModel viewModel = new AdminPanelCreateReviewViewModel(
                createBaseViewModel(principal, "Создание бронирования")
        );
        CreateAdminReviewForm reviewForm = new CreateAdminReviewForm(null, 0, null, 0);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", reviewForm);
        return "admin/create-entity/page-admin-create-review";
    }

    @Override
    @PostMapping("/create/review")
    public String createReview(@Valid @ModelAttribute("form") CreateAdminReviewForm reviewForm,
                                  BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "creating a review from the administrator, bookingId:" + reviewForm.bookingId() + ", ADMIN: " + principal.getName());
        if (bindingResult.hasErrors()) {
            AdminPanelCreateReviewViewModel viewModel = new AdminPanelCreateReviewViewModel(
                    createBaseViewModel(principal, "Создание квеста")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", reviewForm);
            return "admin/create-entity/page-admin-create-review";
        }

        ReviewDto reviewDto = mapper.mapReviewFormToDto(reviewForm);
        reviewService.create(reviewDto);
        return "redirect:../reviews";
    }

    @Override
    @GetMapping("/update/review/{id}")
    public String editForm(@PathVariable int id, Principal principal, Model model) {
        LOG.log(Level.INFO, "the review update page for the administrator, reviewId:" + id + ", ADMIN: " + principal.getName());
        ReviewDto r = reviewService.findById(id);
        AdminPanelUpdateReviewViewModel viewModel = new AdminPanelUpdateReviewViewModel(
                createBaseViewModel(principal, "Обновление бронирования")
        );
        UpdateReviewForm bookingForm = mapper.bookingUpdateForm(r);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", bookingForm);
        return "admin/update-entity/page-admin-update-review";
    }

    @Override
    @PostMapping("/update/review/{id}")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") UpdateReviewForm form,
                       BindingResult bindingResult, Principal principal, Model model) {
        LOG.log(Level.INFO, "changing the feedback from the administrator, reviewId:" + id + ", ADMIN: " + principal.getName());
        if (bindingResult.hasErrors()) {
            AdminPanelUpdateReviewViewModel viewModel = new AdminPanelUpdateReviewViewModel(
                    createBaseViewModel(principal, "Обновление бронирования")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "admin/update-entity/page-admin-update-review";
        }
        ReviewDto reviewDto = mapper.mapReviewFormToDto(form);
        reviewService.update(reviewDto);
        return "redirect:../../reviews";
    }

    @Override
    @PostMapping("/deleted/review/{id}")
    public String deletedQuest(@PathVariable int id) {
        LOG.log(Level.INFO, "deleting a review by the administrator, reviewId:" + id);
        reviewService.deleteById(id);
        return "redirect:../../reviews";
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
