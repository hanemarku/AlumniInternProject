package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventsService eventsService;

    @PostMapping
    public EventGetDto save(@RequestBody EventDto eventDto){
        return eventsService.save(eventDto);
    }

    @GetMapping("{id}")
    public EventGetDto findById(@PathVariable UUID id) { return eventsService.findById(id);}

    @GetMapping
    public List<EventGetDto> findAll(){ return eventsService.findAll();}

    @PatchMapping("{id}")
    public EventGetDto update(@PathVariable UUID id,@RequestBody EventDto edto){
        return eventsService.update(id, edto);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        eventsService.delete(id);
    }
    /*
    *
    @GetMapping("/findBy/{keyword}")
    public List<Events> searchKeyword (@PathVariable("keyword") String keyword){
        return eventsService.findByKeyword(keyword);
    }*/
    @GetMapping("/findBy/{keyword}")
    public Set<EventGetDto> findByKeyword(@PathVariable("keyword") String keyWord,
                                          @RequestBody Set<EventGetDto> eventDtos){
        return eventsService.findByKeyword(keyWord, eventDtos);
    }
}

