package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
