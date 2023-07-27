package com.example.AlumniInternProject.post;



import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostGetDto> findPostsByUser(UUID id);

    PostGetDto save(PostDto postDto);

    List<PostGetDto> findAll();

    PostGetDto findById(UUID id);

    PostGetDto update(UUID id, PostDto postDto);

    void delete(UUID id);
}
