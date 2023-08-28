package com.example.AlumniInternProject.Events;

import com.example.AlumniInternProject.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EventsRepository extends JpaRepository<Events, UUID> {
    @Query("SELECT e FROM Events e WHERE e.id = :uuid")
    Events findEventsById(UUID uuid);

}
