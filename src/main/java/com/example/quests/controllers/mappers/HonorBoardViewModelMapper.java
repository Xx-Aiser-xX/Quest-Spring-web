package com.example.quests.controllers.mappers;

import com.example.quests.dto.UserDto;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.HonorBoardViewModel;
import org.example.questcontracts.viewmodel.cards.HonorBoardCountCardViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HonorBoardViewModelMapper {

    HonorBoardViewModel mapViewModel(BaseViewModel base,
                                     Page<UserDto> usersDto);
    List<HonorBoardCountCardViewModel> mapHonorBoardCountCardViewModel(
            Page<UserDto> usersDto);
}
