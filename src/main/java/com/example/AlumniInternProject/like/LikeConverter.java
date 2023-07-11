package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.entity.Like;

public class LikeConverter {

    public static LikeDto convertLikeEntityToDto(Like like) {
        LikeDto likeDto = new LikeDto();

        likeDto.setId(like.getId());
        likeDto.setUser(like.getUser());
        likeDto.setPost(like.getPost());
        likeDto.setComment(like.getComment());
        likeDto.setDateOfLike(like.getDateOfLike());

        return likeDto;
    }

    public static Like convertLikeDtoToEntity(Like likedBy) {
        Like like = new Like();

        like.setId(likedBy.getId());
        like.setUser(likedBy.getUser());
        like.setPost(likedBy.getPost());
        like.setComment(likedBy.getComment());
        like.setDateOfLike(likedBy.getDateOfLike());

        return like;
    }
}
