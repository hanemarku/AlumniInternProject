package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;

import java.util.List;
import java.util.UUID;

public interface EventsService {
    //Events save(Events event);
    public EventGetDto save(EventDto eventDto);
    List<EventGetDto> findAll();
    EventGetDto findById(UUID id);
    EventGetDto update(UUID id, EventDto edto);
    void delete(UUID id);
}
