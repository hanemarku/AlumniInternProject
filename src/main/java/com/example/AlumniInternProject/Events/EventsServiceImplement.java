package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsServiceImplement implements EventsService {

    public final EventsRepository eventsRepository;

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
    public EventGetDto save(EventDto eventDto) {
        var eDto = new Events(
                eventDto.getName(),
                eventDto.getTopic(),
                eventDto.getDescription(),
                eventDto.getDate(),
                eventDto.isLimitedMembers(),
                eventDto.getMaxParticipants(),
                eventDto.getImgUrl(),
                eventDto.getCities()
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

}
