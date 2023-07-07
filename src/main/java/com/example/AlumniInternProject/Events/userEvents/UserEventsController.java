package com.example.AlumniInternProject.Events.userEvents;

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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events/{eventId}")
public class UserEventsController {
    private final UserEventsService userEventsService;
    private final UserEventsRepository userEventsRepository;
    @GetMapping()
    public List<UserGetDto> getUsersByEventId(@PathVariable Long eventId) {
        List<User> users = userEventsRepository.findUsersByEventId(eventId);
        List<UserGetDto> userGetDtos = users.stream()
                .map(user -> mapUser(user))
                .collect(Collectors.toList());
        return userGetDtos;
    }

    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response, @PathVariable Long eventId) throws IOException {
        List<UserGetDto> listUsers = userEventsService.getUsersByEventId(eventId);
        EventCsvExporter exporter = new EventCsvExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response, @PathVariable Long eventId) throws IOException {
        List<UserGetDto> listUsers = userEventsService.getUsersByEventId(eventId);
        EventExcelExporter exporter = new EventExcelExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response, @PathVariable Long eventId) throws IOException {
        List<UserGetDto> listUsers = userEventsService.getUsersByEventId(eventId);
        EventPdfExporter exporter = new EventPdfExporter();
        exporter.export(listUsers, response);
    }
    @PostMapping
    public UserEventGetDto save(@RequestBody UserEventDto eventDto){
        return userEventsService.save(eventDto);
    }

    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }

}
