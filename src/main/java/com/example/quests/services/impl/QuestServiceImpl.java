package com.example.quests.services.impl;


import com.example.quests.dto.QuestDto;
import com.example.quests.dto.QuestAndOrganizerNameDto;
import com.example.quests.entitys.Organizer;
import com.example.quests.entitys.Quest;
import com.example.quests.entitys.Review;
import com.example.quests.repositories.OrganizerRepository;
import com.example.quests.repositories.QuestRepository;
import com.example.quests.repositories.ReviewRepository;
import com.example.quests.services.QuestService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@EnableCaching
public class QuestServiceImpl implements QuestService {

    private final QuestRepository questRepository;
    private final ReviewRepository reviewRepository;
    private final OrganizerRepository organizerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestServiceImpl(QuestRepository questRepository, ReviewRepository reviewRepository, OrganizerRepository organizerRepository, ModelMapper modelMapper) {
        this.questRepository = questRepository;
        this.reviewRepository = reviewRepository;
        this.organizerRepository = organizerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    @CacheEvict(value = "quests", key = "#questDto.id")
    public void create(QuestAndOrganizerNameDto questDto) {
        Organizer organizer = organizerRepository.findById(Organizer.class, questDto.getOrganizerId());
        if (organizer.isDeleted())
            throw new RuntimeException("organizer deleted");
        Quest quest = modelMapper.map(questDto, Quest.class);
        quest.setOrganizer(organizer);
        questRepository.create(quest);
    }

    @Override
    @CacheEvict(value = "quests", key = "'getAll-' + #page + '-' + #size")
    public Page<QuestDto> getAll(int page, int size) {
        Page<Quest> quests = questRepository.getEntitys(Quest.class, page - 1, size, false);
        return quests.map(quest -> modelMapper.map(quest, QuestDto.class));
    }

    @Override
    @Cacheable(value="quests", key = "'id-' + #id")
    public QuestAndOrganizerNameDto findById(int id) {
        Quest quest = questRepository.findById(Quest.class, id);
        return quest.isDeleted() ? null : modelMapper.map(quest, QuestAndOrganizerNameDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "quests", key = "#questDto.id")
    public void update(QuestAndOrganizerNameDto questDto) {
        Organizer organizer = organizerRepository.findById(Organizer.class, questDto.getOrganizerId());
        if (organizer.isDeleted()) {
            throw new RuntimeException("organizer deleted");
        }
        Quest quest = modelMapper.map(questDto, Quest.class);
        quest.setOrganizer(organizer);
        questRepository.update(quest);
    }

    @Override
    @Transactional
    @CacheEvict(value = "quests", allEntries = true)
    public void deleteById(int id) {
        Quest q = questRepository.findById(Quest.class, id);
        q.setDeleted(true);
        questRepository.update(q);
    }

    @Override
    public Page<QuestAndOrganizerNameDto> getQuestAndNameOrganizer(int page, int size) {
        Page<Quest> quests = questRepository.questsAndOrganizer(page - 1, size, false);
        return quests.map(quest -> modelMapper.map(quest, QuestAndOrganizerNameDto.class));
    }


    @Override
    @Cacheable(value="quests")
    public Page<QuestDto> getQuests(int page, int size, String sort, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Quest> quests = new ArrayList<Quest>();

        if(search != null && !search.equals("")){
            quests = searchQuests(search);
        }
        else{
            switch (sort){
                case "rating":{
                    quests = sortedQuestsRating();
                    break;
                }
                case "popularity":{
                    quests = sortedQuestByPopularity();
                    break;
                }
                case "price":{
                    quests = sortedQuestPrice();
                    break;
                }
                case "random":{
                    quests = sortedQuestRandom();
                    break;
                }
                case "no":{
                    Page<Quest> questsPage = questRepository.getEntitys(Quest.class, page - 1, size, false);
                    return questsPage.map(quest -> modelMapper.map(quest, QuestDto.class));
                }
            }
        }

        List<QuestDto> questDto = quests.stream().map(quest -> modelMapper.map(quest, QuestDto.class)).toList();
        List<QuestDto> pages = questDto.subList((page - 1) * size, Math.min((page - 1) * size + size, questDto.size()));
        return new PageImpl<QuestDto>(pages, pageable, questDto.size());
    }

    @Override
    public void updateRatingQuest(int id, int rating){
        List<Review> reviews = reviewRepository.getAllReviewsQuest(id);
        Quest quest = questRepository.findById(Quest.class, id);
        double sumRating = rating;
        int count = 1;
        for (Review review : reviews) {
            sumRating += review.getRating();
            count++;
        }
        quest.setRating(Math.round((sumRating / count) * 10.0) / 10.0);
        questRepository.update(quest);
    }

    private List<Quest> sortedQuestsRating(){
        List<Quest> quests = questRepository.getAll(Quest.class,false);

        quests.sort((quest1, quest2) -> Double.compare(quest2.getRating(), quest1.getRating()));
        return quests;
    }

    private List<Quest> sortedQuestByPopularity(){
        List<Quest> quests = questRepository.questAndBooking(false);

        Map<Integer, Integer> questBookingCount = new HashMap<>();
        List<Quest> sortedQuests = new ArrayList<>();
        for (Quest quest : quests) {
            questBookingCount.put(quest.getId(), quest.getBooking().size());
            sortedQuests.add(quest);
        }

        sortedQuests.sort((q1, q2) -> Integer.compare(
                questBookingCount.get(q2.getId()), questBookingCount.get(q1.getId())));

        return sortedQuests;
    }

    private List<Quest> sortedQuestPrice(){
        List<Quest> quests = questRepository.getAll(Quest.class,false);

        quests.sort((quest1, quest2) -> Double.compare(quest2.getPrice(), quest1.getPrice()));
        return quests;
    }

    private List<Quest> sortedQuestRandom(){
        List<Quest> quests = questRepository.getAll(Quest.class, false);
        Collections.shuffle(quests);
        return quests;
    }

    private List<Quest> searchQuests(String search){
        List<Quest> quests = questRepository.getAll(Quest.class, false);
        List<Quest> temp = new ArrayList<Quest>();
        for(Quest quest : quests){
            if(quest.getName().toLowerCase().contains(search.toLowerCase()))
                temp.add(quest);
        }
        return temp;
    }

    @Override
    @Cacheable(value = "quests")
    public Page<QuestDto> questsFromTheOrganizer(int id, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Quest> quests = questRepository.questsFromTheOrganizer(id, false);
        List<QuestDto> questDto = quests.stream().map(qu -> modelMapper.map(qu, QuestDto.class)).toList();
        List<QuestDto> pages = questDto.subList((page - 1) * size, Math.min((page - 1) * size + size, questDto.size()));
        return new PageImpl<QuestDto>(pages, pageable, questDto.size());
    }
}
