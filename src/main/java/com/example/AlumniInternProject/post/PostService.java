package com.example.AlumniInternProject.post;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostGetDto> getAllPosts();

    PostGetDto getPostById(UUID id);

    List<PostGetDto> getPostsByUser(UUID user_id);

    PostGetDto createPost(PostDto postDto);

    PostGetDto updatePost(UUID id, PostDto postDto);

    void deletePost(UUID id);

    List<PostGetDto> searchPostsByKeyword(String keyword);

    List<PostGetDto> getPostsByCategory(String category);

    List<PostGetDto> getPostsByTag(String tag);

    List<PostGetDto> getPostsByDateRange(Date startDate, Date endDate);

}

