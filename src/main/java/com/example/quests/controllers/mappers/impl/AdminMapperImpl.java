package com.example.quests.controllers.mappers.impl;

import com.example.quests.controllers.mappers.AdminMapper;
import com.example.quests.dto.*;
import org.example.questcontracts.form.create.*;
import org.example.questcontracts.form.get.*;
import org.example.questcontracts.form.update.*;
import org.example.questcontracts.viewmodel.BaseViewModel;
import org.example.questcontracts.viewmodel.admin.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public AdminPanelOrganizerViewModel mapOrganizerViewModel(BaseViewModel base,
                                                              Page<OrganizerDto> organizerDto){
        AdminPanelOrganizerViewModel viewModel = new AdminPanelOrganizerViewModel(
                base,
                mapOrganizerForm(organizerDto),
                organizerDto.getTotalPages());
        return viewModel;
    }

    @Override
    public List<GetOrganizerForm> mapOrganizerForm(Page<OrganizerDto> organizerDto){
        List<GetOrganizerForm> organizerForm = organizerDto.stream().map(o -> new GetOrganizerForm(
                o.getId(),
                o.getName(),
                o.getPhone(),
                o.getCity(),
                o.getDescription(),
                o.getRating(),
                o.getPhotoUrl())).toList();
        return organizerForm;
    }

    @Override
    public OrganizerRegistrationDto mapOrganizerFormToDto(OrganizerRegistrationForm form){
        OrganizerRegistrationDto ord = new OrganizerRegistrationDto(form.name(), form.email(), form.phone(),form.city(),
                form.description(), form.password(), form.confirmPassword(),form.photoUrl());
        return ord;
    }

    @Override
    public OrganizerDto mapOrganizerFormToDto(UpdateOrganizerForm ogf){
        OrganizerDto ord = new OrganizerDto(ogf.name(), ogf.phone(),
                ogf.city(), ogf.description(), ogf.rating(), ogf.photoUrl());
        ord.setId(ogf.id());
        return ord;
    }

    @Override
    public UpdateOrganizerForm mapOrganizerUpdateForm(OrganizerDto o){
        UpdateOrganizerForm form = new UpdateOrganizerForm(o.getId(), o.getName(),
                o.getPhone(), o.getCity(), o.getDescription(),
                o.getRating(), o.getPhotoUrl());
        return form;
    }



    @Override
    public AdminPanelBookingViewModel mapBookingViewModel(BaseViewModel base,
                                                          Page<BookingDto> bookingDto){
        AdminPanelBookingViewModel viewModel = new AdminPanelBookingViewModel(
                base,
                mapBookingForm(bookingDto),
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
    public BookingDto mapBookingFormToDto(CreateBookingForm bf){
        LocalDate date = LocalDate.parse(bf.date());
        LocalTime time = LocalTime.parse(bf.time());
        BookingDto bd = new BookingDto(bf.participants(), date,
                time, bf.status(), bf.questId(), bf.userId());
        return bd;
    }

    @Override
    public BookingDto mapBookingFormToDto(UpdateBookingForm bf){
        BookingDto bd = new BookingDto(bf.participants(), bf.date(),
                bf.time(), bf.status(), bf.questId(), bf.userId());
        bd.setId(bf.id());
        return bd;
    }




    @Override
    public AdminPanelQuestViewModel mapQuestViewModel(BaseViewModel base,
                                                      Page<QuestAndOrganizerNameDto> questDto){
        AdminPanelQuestViewModel viewModel = new AdminPanelQuestViewModel(
                base,
                mapQuestForms(questDto),
                questDto.getTotalPages());

        return viewModel;
    }

    @Override
    public List<GetQuestForm> mapQuestForms(Page<QuestAndOrganizerNameDto> questDto){
        List<GetQuestForm> questForms = questDto.stream().map(q -> new GetQuestForm(
                q.getId(),
                q.getPhotoUrl(),
                q.getAgeRestriction(),
                q.getGenre(),
                q.getOrganizerId(),
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
    public QuestAndOrganizerNameDto mapQuestFormToDto(CreateQuestForm qf){
        QuestAndOrganizerNameDto qd = new QuestAndOrganizerNameDto(qf.photoUrl(), qf.name(),
                qf.description(), qf.location(), qf.duration(), qf.maxParticipants(),
                qf.price(), qf.difficulty(), qf.ageRestriction(), qf.genre(),
                qf.organizerId());
        return qd;
    }

    @Override
    public QuestAndOrganizerNameDto mapQuestFormToDto(UpdateQuestForm qf){
        QuestAndOrganizerNameDto qd = new QuestAndOrganizerNameDto(qf.photoUrl(), qf.name(),
                qf.description(), qf.location(), qf.duration(), qf.maxParticipants(),
                qf.price(), qf.difficulty(), qf.ageRestriction(), qf.genre(),
                qf.organizerId());
        qd.setId(qf.id());
        return qd;
    }

    @Override
    public UpdateQuestForm questUpdateForm(QuestAndOrganizerNameDto q){
        UpdateQuestForm questForm = new UpdateQuestForm(q.getId(), q.getPhotoUrl(),q.getAgeRestriction(),
                q.getGenre(), q.getOrganizerId(), q.getName(), q.getDescription(), q.getLocation(),
                q.getDuration(), q.getMaxParticipants(), q.getPrice(), q.getDifficulty());
        return questForm;
    }




    @Override
    public AdminPanelReviewViewModel mapReviewViewModel(BaseViewModel base,
                                                        Page<ReviewDto> reviewDto){
        AdminPanelReviewViewModel viewModel = new AdminPanelReviewViewModel(
                base,
                mapReviewFrom(reviewDto),
                reviewDto.getTotalPages());
        return viewModel;
    }

    @Override
    public List<GetReviewForm> mapReviewFrom(Page<ReviewDto> reviewDto){
        List<GetReviewForm> reviewFrom = reviewDto.stream().map(r -> new GetReviewForm(
                r.getId(),
                0,
                r.getReviewText(),
                r.getRating(),
                r.getName(),
                r.getReviewDate(),
                r.getBookingId())).toList();
        return reviewFrom;
    }

    @Override
    public ReviewDto mapReviewFormToDto(CreateAdminReviewForm rf){
        ReviewDto rd = new ReviewDto(rf.rating(), rf.reviewText(), rf.name(), rf.bookingId());
        return rd;
    }

    @Override
    public ReviewDto mapReviewFormToDto(UpdateReviewForm rf){
        ReviewDto rd = new ReviewDto(rf.rating(), rf.reviewText(), rf.name(), rf.bookingId());
        rd.setId(rf.id());
        return rd;
    }

    @Override
    public UpdateReviewForm bookingUpdateForm(ReviewDto r){
        UpdateReviewForm bookingForm = new UpdateReviewForm(r.getId(), r.getReviewText(),
                r.getRating(), r.getName(), r.getReviewDate(), r.getBookingId());
        return bookingForm;
    }




    @Override
    public AdminPanelUserViewModel mapUserViewModel(BaseViewModel base,
                                                    Page<UserDto> userDto){
        AdminPanelUserViewModel viewModel = new AdminPanelUserViewModel(
                base,
                mapUserFrom(userDto),
                userDto.getTotalPages());
        return viewModel;
    }

    @Override
    public List<GetUserFrom> mapUserFrom(Page<UserDto> userDto){
        List<GetUserFrom> userFrom = userDto.stream().map(o -> new GetUserFrom(
                o.getId(),
                o.getName(),
                o.getPhone(),
                o.getCompletedQuests(),
                o.getPhotoUrl())).toList();
        return userFrom;
    }

    @Override
    public UserRegistrationDto userCreateForm(UserRegistrationForm form){
        UserRegistrationDto userDto = new UserRegistrationDto(
                form.name(), form.email(), form.phone(), form.photoUrl(),
                form.password(), form.confirmPassword());
        return userDto;
    }

    @Override
    public UserDto mapUserFormToDto(UpdateUserFrom uf){
        UserDto ud = new UserDto(uf.photoUrl(), uf.name(), uf.phone());
        ud.setId(uf.id());
        ud.setCompletedQuests(uf.completedQuests());
        return ud;
    }

    @Override
    public UpdateUserFrom organizerUpdateForm(UserDto u){
        UpdateUserFrom organizerForm = new UpdateUserFrom(
                u.getId(), u.getName(), u.getPhone(),
                u.getCompletedQuests(), u.getPhotoUrl());
        return organizerForm;
    }
}
