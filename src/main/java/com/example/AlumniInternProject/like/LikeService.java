package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.comment.CommentDto;
import com.example.AlumniInternProject.comment.CommentGetDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface LikeService {

    LikeGetDto save(LikeDto likeDto);

    List<LikeGetDto> findAll();

    LikeGetDto findById(UUID id);

    LikeGetDto update(UUID id, LikeDto likeDto);

    void delete(UUID id);
}
