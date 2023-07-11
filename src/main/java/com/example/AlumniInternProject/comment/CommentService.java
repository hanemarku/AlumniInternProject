package com.example.AlumniInternProject.comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    CommentDto save(CommentDto commentDto);

    List<CommentDto> findAll();

    CommentDto findById(UUID id);

    CommentDto update(UUID id, CommentDto commentDto);

    void delete(UUID id);
}
