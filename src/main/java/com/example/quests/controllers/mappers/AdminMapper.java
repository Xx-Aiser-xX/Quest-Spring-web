package com.example.quests.controllers.mappers;

import com.example.quests.dto.*;
import org.example.questcontracts.form.create.*;
import org.example.questcontracts.form.get.*;
import org.example.questcontracts.form.update.*;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.admin.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminMapper {

    AdminPanelOrganizerViewModel mapOrganizerViewModel(BaseViewModel base,
                                                       Page<OrganizerDto> organizerDto);
    List<GetOrganizerForm> mapOrganizerForm(Page<OrganizerDto> organizerDto);
    OrganizerRegistrationDto mapOrganizerFormToDto(OrganizerRegistrationForm form);
    OrganizerDto mapOrganizerFormToDto(UpdateOrganizerForm ogf);
    UpdateOrganizerForm mapOrganizerUpdateForm(OrganizerDto o);


    AdminPanelBookingViewModel mapBookingViewModel(BaseViewModel base,
                                                   Page<BookingDto> bookingDto);
    List<GetBookingForm> mapBookingForm(Page<BookingDto> bookingDto);
    UpdateBookingForm mapBookingUpdateForm(BookingDto b);
    BookingDto mapBookingFormToDto(CreateBookingForm bf);
    BookingDto mapBookingFormToDto(UpdateBookingForm bf);


    AdminPanelQuestViewModel mapQuestViewModel(BaseViewModel base,
                                               Page<QuestAndOrganizerNameDto> questDto);
    List<GetQuestForm> mapQuestForms(Page<QuestAndOrganizerNameDto> questDto);
    QuestAndOrganizerNameDto mapQuestFormToDto(CreateQuestForm qf);
    QuestAndOrganizerNameDto mapQuestFormToDto(UpdateQuestForm qf);
    UpdateQuestForm questUpdateForm(QuestAndOrganizerNameDto q);


    AdminPanelReviewViewModel mapReviewViewModel(BaseViewModel base,
                                                 Page<ReviewDto> reviewDto);
    List<GetReviewForm> mapReviewFrom(Page<ReviewDto> reviewDto);
    ReviewDto mapReviewFormToDto(CreateAdminReviewForm rf);
    ReviewDto mapReviewFormToDto(UpdateReviewForm rf);
    UpdateReviewForm bookingUpdateForm(ReviewDto r);


    AdminPanelUserViewModel mapUserViewModel(BaseViewModel base,
                                             Page<UserDto> userDto);
    List<GetUserFrom> mapUserFrom(Page<UserDto> userDto);
    UserRegistrationDto userCreateForm(UserRegistrationForm form);
    UserDto mapUserFormToDto(UpdateUserFrom uf);
    UpdateUserFrom organizerUpdateForm(UserDto u);
}
