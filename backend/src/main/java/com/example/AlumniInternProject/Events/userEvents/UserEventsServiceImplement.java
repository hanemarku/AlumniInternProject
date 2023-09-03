package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecificsRepository;
import com.example.AlumniInternProject.Events.EventsRepository;
import com.example.AlumniInternProject.Events.email.EmailService;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationToken;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationTokenRepository;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationTokenService;
import com.example.AlumniInternProject.entity.Events;
import com.example.AlumniInternProject.entity.MembershipRole;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.DTOs.UserGetDto;
import com.example.AlumniInternProject.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEventsServiceImplement implements UserEventsService{

    private final UserRepository userRepository;

    private final UserEventsRepository userEventsRepository;

    private final EventSpecificsRepository eventSpecificsRepository;
    private final EventsRepository eventsRepository;

    private final EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final TemplateEngine templateEngine;
    private UserEventGetDto mapUserEvent(UserEvents userEvents){
        var dto = new UserEventGetDto();
        dto.setId(userEvents.getId());
        dto.setUser(userEvents.getUser());
        dto.setEventSpecifics(userEvents.getEventSpecifics());
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
    public UserEventGetDto mapUserEvents(UserEvents userEvents){
        var obj = new UserEventGetDto();
        obj.setMembershipRole(userEvents.getMembershipRole());
        obj.setUser(userEvents.getUser());
        obj.setEventSpecifics(userEvents.getEventSpecifics());
        obj.setStatus(userEvents.getStatus());
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
    public UserEventGetDto save(UserEventDto userEventDto) {
        /*Needs to be changed the way that we get the user id
        * Should get the user that is loged in*/
        User user = userRepository.findById(userEventDto.getUser().getId()).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: "+ userEventDto.getUser().getId()));

        EventSpecifics eventSpecifics = eventSpecificsRepository.findById(userEventDto.getEventSpecifics().getId()).
                orElseThrow(() -> new IllegalArgumentException("Event not found with ID: "+ userEventDto.getEventSpecifics().getId()));

        if(eventSpecifics.getEvents().getMaxParticipants() == 0){
            throw new IllegalArgumentException("The limit is already reached!");
        }
        Events events = eventsRepository.getReferenceById(eventSpecifics.getEvents().getId());
        events.setMaxParticipants(events.getMaxParticipants() - 1);

      /*Generates the token*/
       String confirmationToken = UUID.randomUUID().toString();


       String link = "http://localhost:8080/api/v1/eventsAndUsers/confirm/" + confirmationToken + "/SuccesfullySaved" ;

       ConfirmationToken token = new ConfirmationToken(
            confirmationToken,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(45),
            userRepository.findById(userEventDto.getUser().getId()).
                    orElseThrow(() -> new IllegalArgumentException
                            ("User not found with ID: "+ userEventDto.getUser().getId()))
        );
       confirmationTokenService.saveConfirmationToken(token);
       /*Sending the email with the generated token*/
        emailService.sendSimpleMail(user.getEmail(),link);

       UserEvents userEvents = new UserEvents(MembershipRole.Member, user, eventSpecifics, Status.PENDING);

       userEvents.setToken(confirmationToken);
       user.setToken(confirmationToken);

       UserEvents saved = userEventsRepository.save(userEvents);

       return mapUserEvent(saved);
    }

    @Override
    @Transactional
    @Async
    public String confirmParticipation(String confirmationToken) {
        /*THE USER WHO NEEDS THE STATUS CHANGED*/
        UserEvents userEvents = userEventsRepository.findUserEventsByToken(confirmationToken);

        ConfirmationToken token = confirmationTokenService
                .getToken(confirmationToken);

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

        Context context = new Context();
        context.setVariable("Eventi", userEvents.getEventSpecifics().getEvents().getName());

        String htmlContent = templateEngine.process("SuccesfullySaved.html", context);

        return htmlContent;
    }

    @Override
    public List<UserEventGetDto> findAll() {
        return userEventsRepository.findAll()
                .stream().map(e-> mapUserEvents(e)).
                collect(Collectors.toList());
    }

    @Override
    public List<UserEvents> getUsersByStatus(Status status){
        List<UserEvents> theUserEvents = userEventsRepository.findAll();
        List<UserEvents> matched = new ArrayList<>(theUserEvents.size());

        for (UserEvents ue: theUserEvents) {
            /*TODO: TEST AFTER THE LOG IN IS CORRECTED*/
            isExpired(ue.getToken());
            /*THIS ONLY WORKS WHEN WRITTEN CORRECTLY IN UPPERCASE AND THE FULL WORD*/
            if(ue.getStatus() == status){
                matched.add(ue);
            }
        }
        if (matched.isEmpty()){
            throw new IllegalArgumentException("No participant with" + status + "found");
        }
        return matched;
    }

    /* TODO: CHECK TO SET EXPIRED STATUS*/
    private boolean isExpired(String token){
        ConfirmationToken toBeChecked = confirmationTokenRepository.findByToken(token);
        UserEvents userEvents = userEventsRepository.findUserEventsByToken(token);
        if(userEvents.getStatus()== Status.CONFIRMED){
            return false;
        }
        // nqs data e tanishme NUK eshte para dates se "skadimit"
        if(!LocalDateTime.now().isBefore(toBeChecked.getExpiredAt())){
            userEvents.setStatus(Status.EXPIRED);
            return true;
        }
        return false;
    }
    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }
}