package com.example.quests.controllers.mappers;

import com.example.quests.dto.QuestDto;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.MainViewModel;
import org.example.questcontracts.viewmodel.cards.QuestCardViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MainViewModelMapper {

    MainViewModel mapViewModel(BaseViewModel base,
                               Page<QuestDto> questDto);
    List<QuestCardViewModel> mapQuestsCardViewModel(Page<QuestDto> questDto);
}
