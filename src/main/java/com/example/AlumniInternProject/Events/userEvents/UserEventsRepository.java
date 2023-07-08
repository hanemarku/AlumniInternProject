package com.example.AlumniInternProject.Events.userEvents;

import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.entity.UserEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserEventsRepository extends JpaRepository<UserEvents, UUID> {
  @Query("SELECT u FROM User u INNER JOIN UserEvents ue ON u.id = ue.user.id" +
          " WHERE ue.eventSpecifics.id = :eventId")
  /*Changed the event id type required to UUID*/
    List<User> findUsersByEventId(@Param("eventId") UUID eventId);
}
