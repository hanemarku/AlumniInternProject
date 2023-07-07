package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventsRepository;
import com.example.AlumniInternProject.entity.MembershipRole;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEventsServiceImplement implements UserEventsService{

    private final UserEventsRepository userEventsRepository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private UserEventGetDto mapUserEvent(UserEvents userEvents){
        var dto = new UserEventGetDto();
        dto.setId(userEvents.getId());
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
//        MembershipRole membershipRole = eventDto.getMembershipRole();
//        MembershipRole membershipRole = eventDto.setMembershipRole(MembershipRole.Creator);

        // Retrieve the User entity by user ID
        User user = userRepository.findById(eventDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + eventDto.getUserId()));

        // Retrieve the Event entity by event ID
        Events event = eventsRepository.findById(eventDto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventDto.getEventId()));

        UserEvents userEvents = new UserEvents(MembershipRole.Creator, user, event);
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
