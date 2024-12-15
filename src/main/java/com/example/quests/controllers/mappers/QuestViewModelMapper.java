package com.example.quests.controllers.mappers;

import com.example.quests.dto.BookingDto;
import com.example.quests.dto.QuestAndOrganizerNameDto;
import com.example.quests.dto.ReviewDto;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.QuestViewModel;
import org.example.questcontracts.viewmodel.cards.QuestScheduleCardViewModel;
import org.example.questcontracts.viewmodel.cards.ReviewCardViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestViewModelMapper {

    QuestViewModel mapViewModel(BaseViewModel base,
                                QuestAndOrganizerNameDto qe,
                                Page<ReviewDto> reviewDto,
                                Page<BookingDto> bk);

    List<ReviewCardViewModel> mapReviews(Page<ReviewDto> reviewDto);

    List<QuestScheduleCardViewModel> mapQuestScheduleCardViewModel(Page<BookingDto> bk);
}
