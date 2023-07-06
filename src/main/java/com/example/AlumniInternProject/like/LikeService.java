package com.example.AlumniInternProject.like;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeService {

    LikeGetDto save(LikeDto likeDto);

    List<LikeGetDto> findAll();

    LikeGetDto findById(UUID id);

    LikeGetDto update(UUID id, LikeDto likeDto);

    void delete(UUID id);

    Optional<Like> findLikeByPost(UUID post_id);
}
