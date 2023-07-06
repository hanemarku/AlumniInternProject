package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventsRepository extends JpaRepository<Events , UUID> {
    @Query("SELECT u FROM User u INNER JOIN UserEvents ue ON u.id = ue.user.id WHERE ue.event.id = :eventId")
    List<User> findUsersByEventId(@Param("eventId") Long eventId);


}
