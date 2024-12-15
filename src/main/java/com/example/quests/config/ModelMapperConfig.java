package com.example.quests.config;

import com.example.quests.dto.BookingAndQuestDto;
import com.example.quests.entitys.Booking;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Booking, BookingAndQuestDto> bookingTypeMap = modelMapper.createTypeMap(Booking.class, BookingAndQuestDto.class);
        bookingTypeMap.addMappings(mapper -> {
            mapper.map(Booking::getQuest, BookingAndQuestDto::setQuestDto);
        });
        return modelMapper;
    }
}