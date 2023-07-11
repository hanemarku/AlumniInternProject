package com.example.AlumniInternProject.post;

import java.util.List;

public interface PostService {

    PostDto getPostById(Integer id);

    List<PostDto> getAllPosts();

    PostDto createPost(PostDto postDto);

    PostDto updatePost(Integer id, PostDto postDto);

    void deletePost(Integer id);
}
