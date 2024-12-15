package com.example.quests.controllers.mappers;

import com.example.quests.dto.*;
import org.example.questcontracts.form.create.CreateBookingForm;
import org.example.questcontracts.form.create.OrganizerCreateQuestForm;
import org.example.questcontracts.form.create.OrganizerUpdateQuestForm;
import org.example.questcontracts.form.get.GetBookingForm;
import org.example.questcontracts.form.get.GetQuestForm;
import org.example.questcontracts.form.update.UpdateBookingForm;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.OrganizerPanelBookingViewModel;
import org.example.questcontracts.viewmodel.OrganizerViewModel;
import org.example.questcontracts.viewmodel.admin.AdminPanelQuestViewModel;
import org.example.questcontracts.viewmodel.cards.QuestCardViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizerViewModelMapper {

    OrganizerViewModel mapViewModel(BaseViewModel base,
                                    OrganizerDto org,
                                    Page<QuestDto> questDto);
    List<QuestCardViewModel> mapQuestCardViewModel(Page<QuestDto> questDto);
    AdminPanelQuestViewModel organizerQuestsViewModel(BaseViewModel base,
                                                      Page<QuestDto> questDto,
                                                      PersonOrganizerDto p);


    List<GetQuestForm> mapQuestForms(Page<QuestDto> questDto, PersonOrganizerDto p);
    QuestAndOrganizerNameDto mapQuestFormToDto(OrganizerCreateQuestForm form,
                                               int organizerId);
    OrganizerUpdateQuestForm mapQuestForm(QuestAndOrganizerNameDto q);
    QuestAndOrganizerNameDto mapQuestFormToDto(OrganizerUpdateQuestForm form,
                                               int organizerId);


    OrganizerPanelBookingViewModel mapBookingViewModel(BaseViewModel base,
                                                       Page<BookingDto> bookingDto,
                                                       int questId);
    List<GetBookingForm> mapBookingForm(Page<BookingDto> bookingDto);
    UpdateBookingForm mapBookingUpdateForm(BookingDto b);
    BookingDto mapBookingFormToDto(CreateBookingForm bf, int questId);
    BookingDto mapBookingFormToDto(UpdateBookingForm bf, int questId);
}
