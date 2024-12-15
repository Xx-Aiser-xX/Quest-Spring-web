package com.example.quests.controllers.mappers.impl;

import com.example.quests.controllers.mappers.HonorBoardViewModelMapper;
import com.example.quests.dto.UserDto;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.HonorBoardViewModel;
import org.example.questcontracts.viewmodel.cards.HonorBoardCountCardViewModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HonorBoardViewModelMapperImpl implements HonorBoardViewModelMapper {

    @Override
    public HonorBoardViewModel mapViewModel(BaseViewModel base,
                                            Page<UserDto> usersDto){
        HonorBoardViewModel viewModel = new HonorBoardViewModel(
                base,
                mapHonorBoardCountCardViewModel(usersDto));
        return viewModel;
    }

    @Override
    public List<HonorBoardCountCardViewModel> mapHonorBoardCountCardViewModel(
            Page<UserDto> usersDto){
        List<HonorBoardCountCardViewModel> honorBoardCountCardViewModel = usersDto.stream()
                .map(u -> new HonorBoardCountCardViewModel(
                        u.getPhotoUrl(),
                        u.getName(),
                        u.getCompletedQuests())).toList();
        return honorBoardCountCardViewModel;
    }
}
