package com.example.AlumniInternProject.like;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeService {

    List<LikeDto> getAllLikes();

    Optional<LikeDto> getLikeById(Integer id);

    LikeDto addLike(LikeDto likeDto);

    void deleteLike(Integer id);

    LikeDto updateLike(Integer id, LikeDto likeDto);
}
