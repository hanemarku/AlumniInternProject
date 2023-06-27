package com.example.AlumniInternProject.skill;

import com.example.AlumniInternProject.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
}
