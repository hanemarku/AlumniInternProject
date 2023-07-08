package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificDto;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventSpecificsServiceImplementation implements
                    EventSpecificsService{

    private final EventSpecificsRepository eventSpecificsRepository;
    private EventSpecificGetDto map(EventSpecifics e){
        var dto = new EventSpecificGetDto();
        dto.setId(e.getId());
        dto.setDate(e.getDate());
        dto.setEvents(e.getEvents());
        dto.setCity(e.getCity());
        return dto;
    }

    @Override
    public EventSpecificGetDto save(EventSpecificDto eventSpecificDto) {
            var eDDto = new EventSpecifics(
                    eventSpecificDto.getDate(),
                    eventSpecificDto.getEvents(),
                    eventSpecificDto.getCity()
            );
            var saved = eventSpecificsRepository.save(eDDto);
            return map(saved);
    }

    @Override
    public EventSpecificGetDto update(EventSpecificDto eventSpecificDto, UUID id) {
        var eDDto = eventSpecificsRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        eDDto.setDate(eventSpecificDto.getDate());
        eDDto.setEvents(eventSpecificDto.getEvents());
        eDDto.setCity(eventSpecificDto.getCity());

        var saved = eventSpecificsRepository.save(eDDto);
        return map(saved);
    }

    @Override
    public List<String> findAll() {
        return eventSpecificsRepository.findAll().
                stream().map(eS -> map(eS).getEvents().getName()).
                collect(Collectors.toList());
    }

    @Override
    public EventSpecificGetDto findById(UUID id) {
        var eOpt = eventSpecificsRepository.findById(id);

        if(eOpt.isPresent()){
            return map(eOpt.get());
        }

        throw new RuntimeException("Event specific with this id does not exist!");
    }
    @Override
    public void delete(UUID id) {
        eventSpecificsRepository.deleteById(id);
    }
}
