package com.example.AlumniInternProject.Events.EventSpecifics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventSpecificsRepository extends JpaRepository<EventSpecifics, UUID> {
}
