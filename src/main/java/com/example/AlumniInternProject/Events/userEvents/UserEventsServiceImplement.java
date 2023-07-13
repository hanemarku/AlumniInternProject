package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecificsRepository;
import com.example.AlumniInternProject.Events.EventSpecifics.dto.EventSpecificGetDto;
import com.example.AlumniInternProject.Events.dto.UserEventRegistrationGetDto;
import com.example.AlumniInternProject.entity.MembershipRole;
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

    /*This save is as a register that the user makes*/
    @Override
    public UserEventGetDto save(UserEventDto eventDto) {
        /*Needs to be changed the way that we get the user id
        * Should get the user that is loged in*/
        User user = userRepository.findById(eventDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: "
                                                                + eventDto.getUserId()));

        EventSpecifics eventSpecifics =
                eventSpecificsRepository.findById(eventDto.getEventSpecificsId())
                         .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: "
                                                                         + eventDto.getEventSpecificsId()));
        UserEvents userEvents = new UserEvents(MembershipRole.Member,
                                                        user, eventSpecifics);
        UserEvents saved = userEventsRepository.save(userEvents);
        return mapUserEvent(saved);
    }

    @Override
    public UserEventGetDto save(UserEventRegistrationGetDto userEventRegistrationDto,
                                EventSpecificGetDto eventDto) {
        User user = userRepository.findById(userEventRegistrationDto.getId()).
                                    orElseThrow(()-> new IllegalArgumentException("User not found!"));
        EventSpecifics eventSpecifics = eventSpecificsRepository.findById(eventDto.getId())
                                        .orElseThrow(()->
                                                new IllegalArgumentException("Event does not exist!"));
        UserEvents userEvents = new UserEvents(MembershipRole.Member,
                                                user , eventSpecifics);
        UserEvents saved = userEventsRepository.save(userEvents);
        return mapUserEvent(saved);
    }

    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }

}