package com.example.quests.controllers.mappers.impl;

import com.example.quests.controllers.mappers.UserViewModelMapper;
import com.example.quests.dto.BookingAndQuestDto;
import com.example.quests.dto.QuestDto;
import com.example.quests.dto.ReviewDto;
import com.example.quests.dto.UserAndEmailDto;
import org.example.questcontracts.form.create.CreateReviewForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.UserViewModel;
import org.example.questcontracts.viewmodel.cards.QuestCardViewModel;
import org.example.questcontracts.viewmodel.cards.UserBookingsCardViewModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserViewModelMapperImpl implements UserViewModelMapper {

    @Override
    public UserViewModel mapUserViewModel(BaseViewModel base, UserAndEmailDto userDto,
                                          List<BookingAndQuestDto> currentBookingDto,
                                          Page<BookingAndQuestDto> pastBookingDto){
        UserViewModel viewModel = new UserViewModel(
                base,
                userDto.getId(),
                userDto.getPhotoUrl(),
                userDto.getName(),
                userDto.getPersonEmail(),
                userDto.getPhone(),
                mapQuestCurrentBookingsCardViewModel(currentBookingDto),
                mapQuestPastBookingsCardViewModel(pastBookingDto),
                pastBookingDto.getTotalPages());

        return viewModel;
    }

    @Override
    public List<UserBookingsCardViewModel> mapQuestCurrentBookingsCardViewModel(
            List<BookingAndQuestDto> currentBookingDto){

        List<UserBookingsCardViewModel> questBookingsCardViewModel = currentBookingDto
                .stream().map(bqd -> createUserBookingsCardViewModel(bqd, bqd.getQuestDto())).toList();

        return questBookingsCardViewModel;
    }

    @Override
    public List<UserBookingsCardViewModel> mapQuestPastBookingsCardViewModel(
            Page<BookingAndQuestDto> pastBookingDto){

        List<UserBookingsCardViewModel> questBookingsCardViewModel = pastBookingDto
                .stream().map(bqd -> createUserBookingsCardViewModel(bqd, bqd.getQuestDto())).toList();

        return questBookingsCardViewModel;
    }

    private UserBookingsCardViewModel createUserBookingsCardViewModel(BookingAndQuestDto bk, QuestDto qu) {
        QuestCardViewModel questCardViewModel = new QuestCardViewModel(
                qu.getPhotoUrl(),
                qu.getName(),
                qu.getRating(),
                qu.getLocation(),
                qu.getMaxParticipants(),
                qu.getPrice(),
                qu.getId());

        UserBookingsCardViewModel userBookingsCardViewModel = new UserBookingsCardViewModel(
                questCardViewModel,
                bk.getId(),
                bk.getDate(),
                bk.getTime(),
                bk.getParticipants());
        return  userBookingsCardViewModel;
    }

    @Override
    public ReviewDto mapReviewFormToDto(CreateReviewForm form){
        ReviewDto reviewDto = new ReviewDto(form.rating(), form.reviewText(),
                form.name(), form.bookingId());
        return reviewDto;
    }
}
