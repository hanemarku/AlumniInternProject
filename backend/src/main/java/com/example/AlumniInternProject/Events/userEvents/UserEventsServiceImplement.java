package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecificsRepository;
import com.example.AlumniInternProject.Events.EventsRepository;
import com.example.AlumniInternProject.Events.email.EmailService;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationToken;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationTokenService;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.MembershipRole;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import com.example.AlumniInternProject.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EventsRepository eventsRepository;
    private UserEventGetDto mapUserEvent(UserEvents userEvents){
        var dto = new UserEventGetDto();
        dto.setId(userEvents.getId());
        dto.setUserId(userEvents.getUser().getId());
        dto.setEventSpecificsId(userEvents.getEventSpecifics().getId());
        return dto;
    }
    public ConfirmationToken map(ConfirmationToken confirmationToken){
        var obj = new ConfirmationToken();
        obj.setToken(confirmationToken.getToken());
        obj.setCreatedAt(confirmationToken.getCreatedAt());
        obj.setExpiredAt(confirmationToken.getExpiredAt());
        obj.setUser(confirmationToken.getUser());
        return obj;
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

    //* TODO: WHEN REGISTERING THE USER CAN BE PART OF MANY EVENTS SO WE SHOULD ALSO CHECK IF HE IS TRYING TO REGISTER HIMSELF AGAIN*/
    @Override
  //  @Transactional
    public UserEventGetDto save(UserEventDto userEventDto) {
        /*Needs to be changed the way that we get the user id
        * Should get the user that is loged in*/
        User user = userRepository.findById(userEventDto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: "+ userEventDto.getUserId()));

        EventSpecifics eventSpecifics = eventSpecificsRepository.findById(userEventDto.getEventSpecificsId()).
                orElseThrow(() -> new IllegalArgumentException("Event not found with ID: "+ userEventDto.getEventSpecificsId()));

        if(eventSpecifics.getEvents().getMaxParticipants() == 0){
            throw new IllegalArgumentException("The limit is already reached!");
        }
        Events events = eventsRepository.getReferenceById(eventSpecifics.getEvents().getId());
        events.setMaxParticipants(events.getMaxParticipants() - 1);

      /*Generates the token*/
       String confirmationToken = UUID.randomUUID().toString();


       String link = "http://localhost:8080/api/v1/eventsAndUsers/confirm/" + confirmationToken;

       ConfirmationToken token = new ConfirmationToken(
            confirmationToken,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(45),
            userRepository.findById(userEventDto.getUserId()).
                    orElseThrow(() -> new IllegalArgumentException
                            ("User not found with ID: "+ userEventDto.getUserId()))
        );
       confirmationTokenService.saveConfirmationToken(token);
       /*Sending the email with the generated token*/
       emailService.sendSimpleMail(user.getEmail(),link);

       UserEvents userEvents = new UserEvents(MembershipRole.Member, user, eventSpecifics, Status.PENDING);
       userEvents.setToken(confirmationToken);
       UserEvents saved = userEventsRepository.save(userEvents);

       return mapUserEvent(saved);
    }

    @Override
    @Transactional
    public String confirmParticipation(String confirmationToken) {
        /*THE USER WHO NEEDS THE STATUS CHANGED*/
        UserEvents userEvents = userEventsRepository.findUserEventsByToken(confirmationToken);

        ConfirmationToken token = confirmationTokenService
                .getToken(confirmationToken);
        /*.orElseThrow(() ->
                        new IllegalStateException("token not found"));*/

        if (token.getConfirmedAt() != null) {
            userEvents.setStatus(Status.CONFIRMED);
            throw new RuntimeException("This user has already confirmed the participation");
        }
        LocalDateTime expiredAt = token.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            userEvents.setStatus(Status.EXPIRED);
            throw new RuntimeException("Your confirmation time has expired!");
        }

        confirmationTokenService.setConfirmedAt(confirmationToken);
        userEvents.setStatus(Status.CONFIRMED);
        return "Kthe";
    }
    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }
}