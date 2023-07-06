package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.user.UserGetDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Service
public interface UserEventsService {
    List<UserGetDto> getUsersByEventId(@PathVariable Long eventId);
    UserEventGetDto save(UserEventDto eventDto);
}
