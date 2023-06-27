package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<PostDto> findByUser(User user);

    List<PostDto> findByTitleContainingIgnoreCase(String keyword);

    List<PostDto> findByCategory(String category);

    List<PostDto> findByTagsContainingIgnoreCase(String tag);
}
