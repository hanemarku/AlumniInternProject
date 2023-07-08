package com.example.AlumniInternProject.Events.EventSpecifics;

import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificDto;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/specifics")
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
    private List<String> findAll(){
        return eventSpecificsService.findAll();
    }
    @GetMapping("{id}")
    private EventSpecificGetDto findById(@PathVariable UUID id){
        return  eventSpecificsService.findById(id);
    }
    @DeleteMapping("{id}")
    private void delete(@PathVariable UUID id){
        eventSpecificsService.delete(id);
    }
}
