package com.example.quests.controllers.mappers.impl;

import com.example.quests.controllers.mappers.OrganizerViewModelMapper;
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
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class OrganizerViewModelMapperImpl implements OrganizerViewModelMapper {

    @Override
    public OrganizerViewModel mapViewModel(BaseViewModel base,
                                           OrganizerDto org,
                                           Page<QuestDto> questDto){
        OrganizerViewModel viewModel = new OrganizerViewModel(
                base,
                org.getId(),
                org.getPhotoUrl(),
                org.getName(),
                org.getRating(),
                org.getDescription(),
                org.getPhone(),
                mapQuestCardViewModel(questDto),
                questDto.getTotalPages());
        return viewModel;
    }

    @Override
    public List<QuestCardViewModel> mapQuestCardViewModel(Page<QuestDto> questDto){
        List<QuestCardViewModel> questCardViewModel = questDto.stream()
                .map(qu -> new QuestCardViewModel(
                        qu.getPhotoUrl(),
                        qu.getName(),
                        qu.getRating(),
                        qu.getLocation(),
                        qu.getMaxParticipants(),
                        qu.getPrice(),
                        qu.getId())).toList();
        return questCardViewModel;
    }

    @Override
    public AdminPanelQuestViewModel organizerQuestsViewModel(BaseViewModel base,
                                                             Page<QuestDto> questDto,
                                                             PersonOrganizerDto p){
        AdminPanelQuestViewModel viewModel = new AdminPanelQuestViewModel(
                base,
                mapQuestForms(questDto, p),
                questDto.getTotalPages());
        return viewModel;
    }

    @Override
    public List<GetQuestForm> mapQuestForms(Page<QuestDto> questDto, PersonOrganizerDto p){
        List<GetQuestForm> questForms = questDto.stream().map(q -> new GetQuestForm(
                q.getId(),
                q.getPhotoUrl(),
                q.getAgeRestriction(),
                q.getGenre(),
                p.getOrganizerId(),
                q.getName(),
                q.getDescription(),
                q.getLocation(),
                q.getDuration(),
                q.getMaxParticipants(),
                q.getPrice(),
                q.getDifficulty(),
                q.getRating())).toList();
        return questForms;
    }

    @Override
    public QuestAndOrganizerNameDto mapQuestFormToDto(OrganizerCreateQuestForm form,
                                                      int organizerId){
        QuestAndOrganizerNameDto dto = new QuestAndOrganizerNameDto(
                form.photoUrl(), form.name(), form.description(), form.location(), form.duration(),
                form.maxParticipants(), form.price(), form.difficulty(), form.ageRestriction(),
                form.genre(), organizerId);
        return dto;
    }

    @Override
    public OrganizerUpdateQuestForm mapQuestForm(QuestAndOrganizerNameDto q){
        OrganizerUpdateQuestForm questForm = new OrganizerUpdateQuestForm(q.getId(), q.getPhotoUrl(),q.getAgeRestriction(),
                q.getGenre(), q.getName(), q.getDescription(), q.getLocation(),
                q.getDuration(), q.getMaxParticipants(), q.getPrice(), q.getDifficulty());
        return questForm;
    }

    @Override
    public QuestAndOrganizerNameDto mapQuestFormToDto(OrganizerUpdateQuestForm form,
                                                      int organizerId){
        QuestAndOrganizerNameDto dto = new QuestAndOrganizerNameDto(
                form.photoUrl(), form.name(), form.description(), form.location(), form.duration(),
                form.maxParticipants(), form.price(), form.difficulty(), form.ageRestriction(),
                form.genre(), organizerId);
        dto.setId(form.id());
        return dto;
    }



    @Override
    public OrganizerPanelBookingViewModel mapBookingViewModel(BaseViewModel base,
                                                              Page<BookingDto> bookingDto,
                                                              int questId){
        OrganizerPanelBookingViewModel viewModel = new OrganizerPanelBookingViewModel(
                base,
                mapBookingForm(bookingDto),
                questId,
                bookingDto.getTotalPages());
        return viewModel;
    }

    @Override
    public List<GetBookingForm> mapBookingForm(Page<BookingDto> bookingDto){
        List<GetBookingForm> bookingForm = bookingDto.stream().map(b -> new GetBookingForm(
                b.getId(),
                b.getParticipants(),
                b.getDate(),
                b.getTime(),
                b.getStatus(),
                b.getQuestId(),
                b.getUserId())).toList();
        return bookingForm;
    }

    @Override
    public UpdateBookingForm mapBookingUpdateForm(BookingDto b){
        UpdateBookingForm bookingForm = new UpdateBookingForm(b.getId(), b.getParticipants(),
                b.getDate(), b.getTime(), b.getStatus(), b.getQuestId(), b.getUserId());
        return bookingForm;
    }

    @Override
    public BookingDto mapBookingFormToDto(CreateBookingForm bf, int questId){
        LocalDate date = LocalDate.parse(bf.date());
        LocalTime time = LocalTime.parse(bf.time());
        BookingDto bd = new BookingDto(bf.participants(), date,
                time, bf.status(), questId, bf.userId());
        return bd;
    }

    @Override
    public BookingDto mapBookingFormToDto(UpdateBookingForm bf, int questId){
        BookingDto bd = new BookingDto(bf.participants(), bf.date(),
                bf.time(), bf.status(), questId, bf.userId());
        bd.setId(bf.id());
        return bd;
    }

}
