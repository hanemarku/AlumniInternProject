package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.dto.EventDto;
import com.example.AlumniInternProject.Events.dto.EventGetDto;
import com.example.AlumniInternProject.Events.dto.EventIdDto;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEventsServiceImplement implements UserEventsService{

    private final UserEventsRepository userEventsRepository;
    private UserEventGetDto mapUserEvent(UserEvents userEvents){
        var dto = new UserEventGetDto();
        dto.setUserId(userEvents.getUser().getId());
        dto.setEventId(userEvents.getEvent().getId());
        return dto;
    }

    @Override
    public List<UserGetDto> getUsersByEventId(Long eventId) {
        List<User> users = userEventsRepository.findUsersByEventId(eventId);
        return users.stream()
                .sorted(Comparator.comparing(User::getFirstname))
                .map(user -> mapUser(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserEventGetDto save(UserEventDto eventDto) {
       var dto = new UserEvents(
               eventDto.getMembershipRole(),
               eventDto.getUserId(),
               eventDto.getEventId()
       );
       var saved = userEventsRepository.save(dto);
       return mapUserEvent(saved);
    }

    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }


}
