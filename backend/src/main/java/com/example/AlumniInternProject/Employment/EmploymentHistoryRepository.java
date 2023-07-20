package com.example.AlumniInternProject.Employment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmploymentHistoryRepository extends JpaRepository<EmploymentHistory, UUID> {
}
