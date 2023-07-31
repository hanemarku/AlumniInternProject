package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Service
public interface EventsService{
    EventGetDto save(EventDto eventDto);
    List<EventGetDto> findAll();
    EventGetDto findById(UUID id);
    EventGetDto update(UUID id, EventDto edto);
    void delete(UUID id);
    Set<EventGetDto> findByKeyword (String keyWord , Set<EventGetDto> eventDtos);
}
