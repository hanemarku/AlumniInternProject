package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostGetDto> getAllPosts();

    PostGetDto getPostById(UUID id);

    List<PostDto> getPostsByUser(User user);

    PostGetDto createPost(PostDto postDto);

    PostGetDto updatePost(UUID id, PostDto postDto);

    void deletePost(UUID id);

    List<PostDto> searchPostsByKeyword(String keyword);

    List<PostDto> getPostsByCategory(String category);

    List<PostDto> getPostsByTag(String tag);

    List<PostDto> getPostsByDateRange(Date startDate, Date endDate);

}

