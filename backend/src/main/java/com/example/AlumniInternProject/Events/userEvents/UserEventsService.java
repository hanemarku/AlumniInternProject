package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationToken;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import jakarta.persistence.Column;
import org.springframework.http.ResponseEntity;
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
    boolean confirmParticipation(String confirmationToken);

}