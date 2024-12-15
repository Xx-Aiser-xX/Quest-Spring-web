package com.example.quests.controllers.mappers.impl;

import com.example.quests.controllers.mappers.MainViewModelMapper;
import com.example.quests.dto.QuestDto;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.MainViewModel;
import org.example.questcontracts.viewmodel.cards.QuestCardViewModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainViewModelMapperImpl implements MainViewModelMapper {

    @Override
    public MainViewModel mapViewModel(BaseViewModel base,
                                      Page<QuestDto> questDto){
        MainViewModel viewModel = new MainViewModel(
                base,
                mapQuestsCardViewModel(questDto),
                questDto.getTotalPages());
        return viewModel;
    }

    @Override
    public List<QuestCardViewModel> mapQuestsCardViewModel(Page<QuestDto> questDto){
        List<QuestCardViewModel> questsCardViewModel = questDto.stream()
                .map(q -> new QuestCardViewModel(
                        q.getPhotoUrl(),
                        q.getName(),
                        q.getRating(),
                        q.getLocation(),
                        q.getMaxParticipants(),
                        q.getPrice(),
                        q.getId())).toList();
        return questsCardViewModel;
    }
}
