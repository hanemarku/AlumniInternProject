package com.example.AlumniInternProject.comment;
import java.util.List;
import java.util.UUID;

public interface CommentService {

    CommentGetDto save(CommentDto commentDto);

    List<CommentGetDto> findAll();

    CommentGetDto findById(UUID id);

    CommentGetDto update(UUID id, CommentDto commentDto);

    List<CommentGetDto> commentsPost(UUID postId);

    void delete(UUID id);
}
