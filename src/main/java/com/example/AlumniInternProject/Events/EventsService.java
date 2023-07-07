package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Service
public interface EventsService{
    //Events save(Events event);
    public EventGetDto save(EventDto eventDto);
    List<EventGetDto> findAll();
    EventGetDto findById(UUID id);
    EventGetDto update(UUID id, EventDto edto);
    void delete(UUID id);
    /*When a user searches with a keyword it returns all the events that include
    * that specific keyword*/
    Set<EventGetDto> findByKeyword (String keyWord , Set<EventGetDto> eventDtos);
    /*Sorting by date ASC*/
    List<EventGetDto> orderAsc(Set<EventGetDto> eventDtos);
    /*Sorting by date DESC*/
    List<EventGetDto> orderDesc(Set<EventGetDto> eventDtos);

    // TO DO
    /*User to register himself on an event */
    /*User to be able to unregister himself from the event*/
    /*The creator to be able to change the roles of a member*/
    /*The crator to be able to calculate events statistic*/
    /*The crator to be able to generate events statistic*/
    /*To track event status*/

}
