package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificDto;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/specifics")
@CrossOrigin("http://localhost:4200")
public class EventSpecificsController {
    private final EventSpecificsService eventSpecificsService;
    @PostMapping
    private EventSpecificGetDto save(@RequestBody EventSpecificDto eventSpecificDto){
        return eventSpecificsService.save(eventSpecificDto);
    }
    @PatchMapping("{id}")
    private EventSpecificGetDto update(@RequestBody EventSpecificDto eventSpecificDto,
                                       @PathVariable UUID id){
        return eventSpecificsService.update(eventSpecificDto , id);
    }
    @GetMapping
    private List<EventSpecificGetDto> findAll(){
        return eventSpecificsService.findAll();
    }
    @GetMapping("{id}")
    private EventSpecificGetDto findById(@PathVariable UUID id){
        return  eventSpecificsService.findById(id);
    }

    @GetMapping("/event-specifics/{id}")
    private List<EventSpecifics> findByEventId(@PathVariable("id") String id){
        return eventSpecificsService.findByEventId(id);
    }
    @DeleteMapping("{id}")
    private void delete(@PathVariable UUID id){
        eventSpecificsService.delete(id);
    }

    @DeleteMapping("/event/{id}")
    private void deleteByEvent(@PathVariable("id") UUID id){
        eventSpecificsService.deleteEventSpecificsByEvents_Id(id);
    }

    @GetMapping("/orderByDate/asc")
    private List<EventSpecificGetDto> orderAsc(@RequestBody Set<EventSpecificGetDto> eventDtos){
        return eventSpecificsService.orderAsc(eventDtos);
    }
    @GetMapping("/orderByDate/desc")
    private List<EventSpecificGetDto> orderDesc(@RequestBody Set<EventSpecificGetDto> eventDtos){
        return eventSpecificsService.orderDesc(eventDtos);
    }

    @GetMapping("/findBy/{keyword}")
    private Set<EventSpecificGetDto> findByKeyword(@PathVariable("keyword") String keyWord,
                                                   @RequestBody Set<EventSpecificGetDto> eventDtos){
        return eventSpecificsService.findByKeyword(keyWord, eventDtos);
    }
}
