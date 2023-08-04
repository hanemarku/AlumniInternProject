package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.export.EventCsvExporter;
import com.example.AlumniInternProject.Events.export.EventExcelExporter;
import com.example.AlumniInternProject.Events.export.EventPdfExporter;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import com.example.AlumniInternProject.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/eventsAndUsers")
public class UserEventsController {
    private final UserEventsService userEventsService;
    private final UserEventsRepository userEventsRepository;
    private final UserRepository userRepository;

    @GetMapping("/events/{eventId}/users")
    public List<UserGetDto> getUsersByEventId(@PathVariable UUID eventId) {
        List<User> users = userEventsRepository.findUsersByEventId(eventId);
        List<UserGetDto> userGetDtos = users.stream()
                .map(user -> mapUser(user))
                .collect(Collectors.toList());
        return userGetDtos;
    }

    @GetMapping("/{eventId}/users/export/csv")
    public void exportToCSV(HttpServletResponse response, @PathVariable UUID eventId) throws IOException {
        List<UserGetDto> listUsers = userEventsService.getUsersByEventId(eventId);
        EventCsvExporter exporter = new EventCsvExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/{eventId}/users/export/excel")
    public void exportToExcel(HttpServletResponse response, @PathVariable UUID eventId) throws IOException {
        List<UserGetDto> listUsers = userEventsService.getUsersByEventId(eventId);
        EventExcelExporter exporter = new EventExcelExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/{eventId}/users/export/pdf")
    public void exportToPDF(HttpServletResponse response, @PathVariable UUID eventId) throws IOException {
        List<UserGetDto> listUsers = userEventsService.getUsersByEventId(eventId);
        EventPdfExporter exporter = new EventPdfExporter();
        exporter.export(listUsers, response);
    }
    @PostMapping
    public UserEventGetDto save(@RequestBody UserEventDto eventDto){
        return userEventsService.save(eventDto);
    }
    @GetMapping("/confirm/{token}/SuccesfullySaved")
    public String confirmParticipation(@PathVariable("token")String confirmationToken){
       return userEventsService.confirmParticipation(confirmationToken);
    }
    @GetMapping("/listAll")
    public List<UserEventGetDto> findAll(){
        return userEventsService.findAll();
    }
    @GetMapping("/{status}")
    public List<UserEvents> getUsersByStatus(@PathVariable("status") Status status){
        return userEventsService.getUsersByStatus(status);
    }
    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }

}