package com.example.quests.controllers.mappers;

import com.example.quests.dto.*;
import org.example.questcontracts.form.create.CreateReviewForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.UserViewModel;
import org.example.questcontracts.viewmodel.cards.UserBookingsCardViewModel;
import org.springframework.data.domain.Page;

import java.util.List;
public interface UserViewModelMapper {
    UserViewModel mapUserViewModel(BaseViewModel base, UserAndEmailDto userDto,
                                          List<BookingAndQuestDto> currentBookingDto,
                                          Page<BookingAndQuestDto> pastBookingDto);

    List<UserBookingsCardViewModel> mapQuestCurrentBookingsCardViewModel(
            List<BookingAndQuestDto> currentBookingDto);

    List<UserBookingsCardViewModel> mapQuestPastBookingsCardViewModel(
            Page<BookingAndQuestDto> pastBookingDto);

    ReviewDto mapReviewFormToDto(CreateReviewForm form);
}
