package com.example.AlumniInternProject.Events.EventSpecifics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EventSpecificsRepository extends JpaRepository<EventSpecifics, UUID> {
    @Query("SELECT e FROM EventSpecifics e WHERE e.events.id = :uuid")
    void findEventSpecificsByEvents_Id(UUID uuid);
}
