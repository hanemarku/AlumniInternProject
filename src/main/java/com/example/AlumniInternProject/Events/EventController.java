package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import com.example.AlumniInternProject.Events.export.EventCsvExporter;
import com.example.AlumniInternProject.Events.export.EventExcelExporter;
import com.example.AlumniInternProject.Events.export.EventPdfExporter;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.user.UserGetDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventsService eventsService;
    private final EventsRepository eventsRepository;

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
        TO BE FIXED
    this is conflicting with the other get method*/
    @GetMapping("{keyWord}")
    public List<EventGetDto> findByKeyWord(@PathVariable String keyWord,@RequestBody List<EventGetDto> eventDtos){
        return eventsService.findByKeyWord(keyWord, eventDtos);
    }

    @GetMapping("/events/{eventId}/users")
    public List<UserGetDto> getUsersByEventId(@PathVariable Long eventId) {
        List<User> users = eventsRepository.findUsersByEventId(eventId);
        List<UserGetDto> userGetDtos = users.stream()
                .map(user -> mapUser(user))
                .collect(Collectors.toList());
        return userGetDtos;
    }

    @GetMapping("/events/{eventId}/users/export/csv")
    public void exportToCSV(HttpServletResponse response, @PathVariable Long eventId) throws IOException {
        List<UserGetDto> listUsers = eventsService.getUsersByEventId(eventId);
        EventCsvExporter exporter = new EventCsvExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/events/{eventId}/users/export/excel")
    public void exportToExcel(HttpServletResponse response, @PathVariable Long eventId) throws IOException {
        List<UserGetDto> listUsers = eventsService.getUsersByEventId(eventId);
        EventExcelExporter exporter = new EventExcelExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/events/{eventId}/users/export/pdf")
    public void exportToPDF(HttpServletResponse response, @PathVariable Long eventId) throws IOException {
        List<UserGetDto> listUsers = eventsService.getUsersByEventId(eventId);
        EventPdfExporter exporter = new EventPdfExporter();
        exporter.export(listUsers, response);
    }

    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }


}

