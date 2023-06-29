package com.example.AlumniInternProject.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<PostGetDto> findByUser(UUID user_id);

    List<PostGetDto> findPostsByKeyword(String keyword);

    List<PostGetDto> findByCategory(String category);

    List<PostGetDto> findByTagContainingIgnoreCase(String tag);
}
