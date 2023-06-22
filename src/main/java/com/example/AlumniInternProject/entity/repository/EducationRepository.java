package com.example.AlumniInternProject.entity.repository;

import com.example.AlumniInternProject.entity.EducationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface EducationRepository extends JpaRepository<EducationHistory, UUID> {
}
