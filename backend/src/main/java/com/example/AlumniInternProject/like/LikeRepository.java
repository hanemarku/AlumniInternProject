package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
}
