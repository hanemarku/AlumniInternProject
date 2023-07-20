package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
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
    /*When a user searches with a keyword it returns all the events that include
    * that specific keyword*/
    Set<EventGetDto> findByKeyword (String keyWord , Set<EventGetDto> eventDtos);

    /*The creator to be able to change the roles of a member*/
    /*The crator to be able to calculate events statistic*/
    /*The crator to be able to generate events statistic*/

}
