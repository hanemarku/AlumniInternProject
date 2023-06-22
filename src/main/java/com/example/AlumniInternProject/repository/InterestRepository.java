package com.example.AlumniInternProject.repository;

import com.example.AlumniInternProject.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterestRepository extends JpaRepository<Interest, UUID> {
}
