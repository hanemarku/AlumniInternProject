package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@Component
public interface UserEventsService {
    List<UserGetDto> getUsersByEventId(@PathVariable UUID eventId);
    UserEventGetDto save(UserEventDto eventDto);
    String confirmParticipation(String confirmationToken);
    List<UserEventGetDto> findAll();
    List<UserEvents> getUsersByStatus(Status status);
}