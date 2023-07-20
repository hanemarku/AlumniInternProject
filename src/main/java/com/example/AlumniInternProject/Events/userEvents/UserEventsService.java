package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import com.example.AlumniInternProject.Events.dto.UserEventRegistrationDto;
import com.example.AlumniInternProject.Events.dto.UserEventRegistrationGetDto;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public interface UserEventsService {
    List<UserGetDto> getUsersByEventId(@PathVariable UUID eventId);
    UserEventGetDto save(UserEventDto eventDto);
    UserEventGetDto save(UserEventRegistrationGetDto userEventRegistrationDto,
                         EventSpecificGetDto eventDto);

}
