package com.example.quests.controllers.mappers.impl;

import com.example.quests.controllers.mappers.QuestViewModelMapper;
import com.example.quests.dto.BookingDto;
import com.example.quests.dto.QuestAndOrganizerNameDto;
import com.example.quests.dto.ReviewDto;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.QuestViewModel;
import org.example.questcontracts.viewmodel.cards.QuestScheduleCardViewModel;
import org.example.questcontracts.viewmodel.cards.ReviewCardViewModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestViewModelMapperImpl implements QuestViewModelMapper {

    @Override
    public QuestViewModel mapViewModel(BaseViewModel base,
                                       QuestAndOrganizerNameDto qe,
                                       Page<ReviewDto> reviewDto,
                                       Page<BookingDto> bk){
        QuestViewModel viewModel = new QuestViewModel(
                base,
                qe.getId(),
                qe.getPhotoUrl(),
                qe.getName(),
                qe.getRating(),
                qe.getAgeRestriction(),
                qe.getDuration(),
                qe.getGenre(),
                qe.getOrganizerName(),
                qe.getOrganizerId(),
                qe.getDescription(),
                mapQuestScheduleCardViewModel(bk),
                mapReviews(reviewDto),
                bk.getTotalPages(),
                reviewDto.getTotalPages());
        return  viewModel;

    }

    @Override
    public List<ReviewCardViewModel> mapReviews(Page<ReviewDto> reviewDto){
        List<ReviewCardViewModel> reviews = reviewDto.stream()
                .map(rv -> new ReviewCardViewModel(
                        rv.getReviewDate(),
                        rv.getReviewText(),
                        rv.getRating(),
                        rv.getName())).toList();
        return reviews;
    }

    @Override
    public List<QuestScheduleCardViewModel> mapQuestScheduleCardViewModel(Page<BookingDto> bk){
        List<QuestScheduleCardViewModel> questScheduleCardViewModel = bk.stream()
                .map(bkd -> new QuestScheduleCardViewModel(
                        bkd.getId(),
                        bkd.getDate(),
                        bkd.getTime(),
                        bkd.getStatus())).toList();
        return questScheduleCardViewModel;
    }
}
