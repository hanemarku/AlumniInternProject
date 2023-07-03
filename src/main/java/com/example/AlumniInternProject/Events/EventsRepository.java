package com.example.AlumniInternProject.Events;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventsRepository extends JpaRepository<Events , UUID> {
}
