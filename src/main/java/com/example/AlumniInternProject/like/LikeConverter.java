package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.comment.CommentConverter;
import com.example.AlumniInternProject.post.PostConverter;
import com.example.AlumniInternProject.user.UserConverter;

public class LikeConverter {

    public static LikeDto convertLikeEntityToDto(LikeEntity likeEntity) {
        LikeDto likeDto = new LikeDto();

        likeDto.setId(likeEntity.getId());
        likeDto.setUser(UserConverter.convertUserEntityToDto(likeEntity.getUser()));
        likeDto.setPost(PostConverter.convertPostEntityToDto(likeEntity.getPost()));
        likeDto.setComment(CommentConverter.convertCommentToDto(likeEntity.getComment()));
        likeDto.setDateOfLike(likeEntity.getDateOfLike());

        return likeDto;
    }


    public static LikeEntity convertLikeDtoToEntity(LikeDto likedBy) {
        LikeEntity likeEntity = new LikeEntity();

        likeEntity.setId(likedBy.getId());
        likeEntity.setUser(UserConverter.convertUserDtoToEntity(likedBy.getUser()));
        likeEntity.setPost(PostConverter.convertPostDtoToEntity(likedBy.getPost()));
        likeEntity.setComment(CommentConverter.convertCommentDtoToEntity(likedBy.getComment()));
        likeEntity.setDateOfLike(likedBy.getDateOfLike());

        return likeEntity;
    }


}
