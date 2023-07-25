package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecifics;
import com.example.AlumniInternProject.Events.EventSpecifics.EventSpecificsRepository;
import com.example.AlumniInternProject.Events.email.EmailService;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationToken;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationTokenRepository;
import com.example.AlumniInternProject.Events.userEvents.ConfirmationToken.ConfirmationTokenService;
import com.example.AlumniInternProject.entity.MembershipRole;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import com.example.AlumniInternProject.user.UserGetDto;
import com.example.AlumniInternProject.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    private final ConfirmationTokenRepository confirmationTokenRepository;
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
    @Override
  //  @Transactional
    public UserEventGetDto save(UserEventDto eventDto) {
        /*Needs to be changed the way that we get the user id
        * Should get the user that is loged in*/
        User user = userRepository.findById(eventDto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: "+ eventDto.getUserId()));
      /*Generates the token*/
       String confirmationToken = UUID.randomUUID().toString();
       user.setConfirmationToken(confirmationToken);

       String link = "http://localhost:8080/api/v1/confirm/" + confirmationToken;

       ConfirmationToken token = new ConfirmationToken(
            confirmationToken,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(45),
            userRepository.findById(eventDto.getUserId()).
                    orElseThrow(() -> new IllegalArgumentException
                            ("User not found with ID: "+ eventDto.getUserId()))
        );
       confirmationTokenService.saveConfirmationToken(token);
       /*Sending the email with the generated token*/
       emailService.sendSimpleMail(user.getEmail(),link);

       EventSpecifics eventSpecifics = eventSpecificsRepository.findById(eventDto.getEventSpecificsId()).
                orElseThrow(() -> new IllegalArgumentException("Event not found with ID: "+ eventDto.getEventSpecificsId()));

       if(user.isVerified() == false){
           return null;
       } else {
           UserEvents userEvents = new UserEvents(MembershipRole.Member, user, eventSpecifics);
           UserEvents saved = userEventsRepository.save(userEvents);

           return mapUserEvent(saved);
       }
    }

    @Override
    @Transactional
    public boolean confirmParticipation(String confirmationToken) {
        User user = userRepository.findUserEventsByConfirmationToken(confirmationToken);
        ConfirmationToken token = confirmationTokenService
                                    .getToken(confirmationToken).orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (token.getConfirmedAt() != null) {
            user.setVerified(false);
            return false;
        }
        LocalDateTime expiredAt = token.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            user.setVerified(false);
            return false;
        }

            confirmationTokenService.setConfirmedAt(confirmationToken);
            user.setVerified(true);
            userRepository.confirmUser(token.getUser().getEmail());

            return true;
    }
    @Transactional
    public UserEvents findUserEventsByUser(User user) {
        return userEventsRepository.findUserEventsByUser(user);
    }

    private UserGetDto mapUser(User user) {
        UserGetDto dto = new UserGetDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }
}