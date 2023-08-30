package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificDto;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    public List<EventSpecificGetDto> findAll() {
        return eventSpecificsRepository.findAll().
                stream().map(eS -> map(eS)).
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

    @Override
    @Transactional
    public void deleteEventSpecificsByEvents_Id(UUID id) {
        List<EventSpecifics> listOfEventSpecifics = eventSpecificsRepository.findAll();
        for(EventSpecifics es : listOfEventSpecifics){
            if (es.getId().toString().toLowerCase().contains(id.toString().toLowerCase(Locale.ROOT))){
                delete(es.getId());
            }
        }

      //  eventSpecificsRepository.deleteEventSpecificsByEvents_Id(id);
    }

    @Override
    public List<EventSpecificGetDto> orderAsc(Set<EventSpecificGetDto> eventDtos) {
        List<EventSpecificGetDto> order = eventDtos.stream().
                sorted(Comparator.comparing(EventSpecificGetDto::getDate)).
                collect(Collectors.toList());
        return order;
    }

    @Override
    public List<EventSpecificGetDto> orderDesc(Set<EventSpecificGetDto> eventDtos) {
        List<EventSpecificGetDto> order = eventDtos.stream().
                sorted(Comparator.comparing(EventSpecificGetDto::getDate).reversed()).
                collect(Collectors.toList());
        return order;
    }
    /*Find by keyword*/
    @Override
    public Set<EventSpecificGetDto> findByKeyword(String keyWord,
                                                  Set<EventSpecificGetDto> eventSDtos) {
        Set<EventSpecificGetDto> matched = new HashSet<>();

        for(EventSpecificGetDto esDto : eventSDtos){
            if(esDto.getEvents().getName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
               || esDto.getEvents().getTopic().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || esDto.getEvents().getDescription().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || esDto.getCity().getName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || esDto.getCity().getCountry().getName().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
                    || esDto.getCity().getCountry().getCode().toLowerCase().contains(keyWord.toLowerCase(Locale.ROOT))
            ){
                matched.add(esDto);
            }
        }
        return matched;
    }

}
