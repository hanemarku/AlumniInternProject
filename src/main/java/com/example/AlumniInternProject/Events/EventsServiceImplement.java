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
        dto.setLimitedMembers(e.isLimitedMembers());
        dto.setMaxParticipants(e.getMaxParticipants());
        dto.setImgUrl(e.getImgUrl());
        dto.setCities(e.getCities());
        return dto;
    }

    //public Events save(Events event) {        return null;    }
    @Override
    public EventGetDto save(EventDto edto) {
        var eDto = new Events(
                edto.getName(),
                edto.getTopic(),
                edto.getDescription(),
                edto.getDate(),
                edto.isLimitedMembers(),
                edto.getImgUrl(),
                edto.getMaxParticipants(),
                edto.getCities()
        );
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
        e.setLimitedMembers(edto.isLimitedMembers());
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


    /*
    *  @Override
    public List<Events> findByKeyword(String keyword) {
        return eventsRepository.findByKeyword(keyword);
    }*/
}
