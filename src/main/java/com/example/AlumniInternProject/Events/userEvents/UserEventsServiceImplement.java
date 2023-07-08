package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecificsRepository;
import com.example.AlumniInternProject.Events.EventsRepository;
import com.example.AlumniInternProject.Events.MembershipRole;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEventsServiceImplement implements UserEventsService{

    private final UserEventsRepository userEventsRepository;
    private final UserRepository userRepository;
    private final EventSpecificsRepository eventSpecificsRepository;
    private UserEventGetDto mapUserEvent(UserEvents userEvents){
        var dto = new UserEventGetDto();
        dto.setId(userEvents.getId());
        dto.setUserId(userEvents.getUser().getId());
        dto.setEventSpecificsId(userEvents.getEventSpecifics().getId());
        return dto;
    }

    @Override
    public List<UserGetDto> getUsersByEventId(UUID eventId) {
        List<User> users = userEventsRepository.findUsersByEventId(eventId);
        return users.stream()
                .sorted(Comparator.comparing(User::getFirstname))
                .map(user -> mapUser(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserEventGetDto save(UserEventDto eventDto) {
        User user = userRepository.findById(eventDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + eventDto.getUserId()));

        EventSpecifics eventSpecifics =
                eventSpecificsRepository.findById(eventDto.getEventSpecificsId())
                         .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: "
                                        + eventDto.getEventSpecificsId()));
    /*When crating the event by default the one creating it is the creator*/
        UserEvents userEvents = new UserEvents(MembershipRole.Creator,
                                                        user, eventSpecifics);
        UserEvents saved = userEventsRepository.save(userEvents);

        return mapUserEvent(saved);
    }

    /*
    *     @Override
        public UserEventGetDto save(UserEventDto eventDto) {
           var dto = new UserEvents(
                   eventDto.getMembershipRole(),
                   eventDto.getUserId(),
                   eventDto.getEventId()
           );
           var saved = userEventsRepository.save(dto);
           return mapUserEvent(saved);
        }
    */
    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }


}
