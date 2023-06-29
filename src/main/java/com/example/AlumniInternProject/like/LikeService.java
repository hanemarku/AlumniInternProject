package com.example.AlumniInternProject.like;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@Service
public interface LikeService {

    private final LikeRepository likeRepository;

    LikeGetDto save(LikeDto likeDto);

    List<LikeGetDto> getLikesOfUser()
}
