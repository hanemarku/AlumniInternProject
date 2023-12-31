package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import com.example.AlumniInternProject.entity.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        dto.setMaxParticipants(e.getMaxParticipants());
        dto.setImgUrl(e.getImgUrl());
        dto.setCreatedBy(e.getCreatedBy());
        return dto;
    }

    @Override
    public EventGetDto save(EventDto edto) {
        var eDto = new Events(
                edto.getName(),
                edto.getTopic(),
                edto.getDescription(),
                edto.getMaxParticipants(),
                edto.getImgUrl(),
                edto.getEventSpecifics(),
                edto.getCreatedBy()
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
        e.setMaxParticipants(edto.getMaxParticipants());
        e.setImgUrl(edto.getImgUrl());
        var saved = eventsRepository.save(e);
        return map(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        eventsRepository.deleteById(id);
    }

    @Override
    public Set<EventGetDto> findByKeyword(String keyWord, Set<EventGetDto> eventDtos) {
        Set<EventGetDto> matched = new HashSet<>();

        for(EventGetDto eventDto : eventDtos){
            if(
                    eventDto.getName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                            || eventDto.getTopic().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                            || eventDto.getDescription().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))){
                matched.add(eventDto);
            }
        }
        return matched;
    }
    /*TODO: DO NOT USE CONTAIN CUZ IT WILL ALWAYS CONTAIN EVEN A LETTER*/
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
