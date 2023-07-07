package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import com.example.AlumniInternProject.entity.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsServiceImplement implements EventsService {

    private final EventsRepository eventsRepository;

    private EventGetDto map(Events e){
        var dto = new EventGetDto();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setTopic(e.getTopic());
        dto.setDescription(e.getDescription());
        dto.setDate(e.getDate());
        dto.setMaxParticipants(e.getMaxParticipants());
        dto.setImgUrl(e.getImgUrl());
        dto.setCities(e.getCities());
        return dto;
    }

    @Override
    public EventGetDto save(EventDto edto) {
        if(eventExists(edto)){
            throw new
                    RuntimeException("The event you are trying to create already exists");
        }
        var eDto = new Events(
                edto.getName(),
                edto.getTopic(),
                edto.getDescription(),
                edto.getDate(),
                edto.getImgUrl(),
                edto.getMaxParticipants(),
                edto.getCities()
        );
        /*The part of the code that needs to be done
        * when the login is finished. This is only a dumb
        * part of code i saw on net :)*/
       // eDto.setCreatedBy(SecurityContextHolder.getContext().getAuthentication());
        var saved = eventsRepository.save(eDto);
        return map(saved);
    }

    @Override
    public List<EventGetDto> findAll() {

        return eventsRepository.findAll()
                .stream().map(e -> map(e))
                .collect(Collectors.toList());
    }

    @Override
    public EventGetDto findById(UUID id) {
        var event = eventsRepository.findById(id);

        if(event.isPresent()){
            return map(event.get());
        }

        throw new RuntimeException("Nuk ekziston eventi me kete id!");
    }

    @Override
    public EventGetDto update(UUID id, EventDto edto) {
        var e = eventsRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        e.setName(edto.getName());
        e.setTopic(edto.getTopic());
        e.setDescription(edto.getDescription());
        e.setDate(edto.getDate());
        e.setMaxParticipants(edto.getMaxParticipants());
        e.setImgUrl(edto.getImgUrl());
        e.setCities(edto.getCities());
        var saved = eventsRepository.save(e);
        return map(saved);
    }

    @Override
    public void delete(UUID id) {
        eventsRepository.deleteById(id);
    }

    /*Searching by keyword. String elements :
     * Name , Topic and Description. I am searching for
     * the city in another method */
    @Override
    public Set<EventGetDto> findByKeyword(String keyWord, Set<EventGetDto> eventDtos) {
        Set<EventGetDto> matched = new HashSet<>();

        for(EventGetDto eventDto : eventDtos){
            if(
                    eventDto.getName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                            || eventDto.getTopic().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                            || eventDto.getDescription().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                            || isCity(keyWord, eventDto) == true
            ){
                matched.add(eventDto);
            }
        }
        return matched;
    }

    /*Return true when it matches the city*/
    public boolean isCity(String city, EventGetDto eventDtos){
        Set<City> cityEvent = eventDtos.getCities();
        for(City c : cityEvent){
            if (c.getName().toLowerCase().contains(city.toLowerCase(Locale.ROOT))){
                return true;
            }
        }
        // ? , sepse do e kthej gjithsesi nje false ne fund
        return false;
    }
    @Override
    public List<EventGetDto> orderAsc(Set<EventGetDto> eventDtos) {
        List<EventGetDto> order = eventDtos.stream().
                sorted(Comparator.comparing(EventGetDto::getDate)).
                collect(Collectors.toList());
        return order;
    }

    @Override
    public List<EventGetDto> orderDesc(Set<EventGetDto> eventDtos) {
        List<EventGetDto> order = eventDtos.stream().
                sorted(Comparator.comparing(EventGetDto::getDate).reversed()).
                collect(Collectors.toList());
        return order;
    }

    /*When creating a new event we also need to make sure
    * that the event is not dublicated.
    *   1. Event name must be different
    * (if the event name is the same we can add
    * only date and city)
     */
    public boolean eventExists(EventDto eventDto){
        for (EventGetDto eventGetDto : findAll()){
            if (eventDto.getName().toLowerCase().
                    contains(eventGetDto.getName().
                            toLowerCase(Locale.ROOT)))
                return true;
        }
        return false;
    }

}
