package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificDto;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public interface EventSpecificsService {
    /*The creator can save details*/
    EventSpecificGetDto save(EventSpecificDto eventSpecificDto);
    /*The creator can update details*/
    EventSpecificGetDto update(EventSpecificDto eventSpecificDto, UUID id);
    List<EventSpecificGetDto> findAll();
    EventSpecificGetDto findById(UUID id);
    void delete(UUID id);
    void deleteEventSpecificsByEvents_Id(UUID id);
    List<EventSpecificGetDto> orderAsc(Set<EventSpecificGetDto> eventDtos);
    List<EventSpecificGetDto> orderDesc(Set<EventSpecificGetDto> eventDtos);
    Set<EventSpecificGetDto> findByKeyword(String keyWord, Set<EventSpecificGetDto> eventDtos);
}