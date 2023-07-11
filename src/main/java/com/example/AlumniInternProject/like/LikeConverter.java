package com.example.AlumniInternProject.like;

public class LikeConverter {

    public static LikeDto convertLikeEntityToDto(LikeEntity likeEntity) {
        LikeDto likeDto = new LikeDto();

        likeDto.setId(likeEntity.getId());
        likeDto.setUser(likeEntity.getUser());
        likeDto.setPost(likeEntity.getPost());
        likeDto.setComment(likeEntity.getComment());
        likeDto.setDateOfLike(likeEntity.getDateOfLike());

        return likeDto;
    }

    public static LikeEntity convertLikeDtoToEntity(LikeEntity likedBy) {
        LikeEntity likeEntity = new LikeEntity();

        likeEntity.setId(likedBy.getId());
        likeEntity.setUser(likedBy.getUser());
        likeEntity.setPost(likedBy.getPost());
        likeEntity.setComment(likedBy.getComment());
        likeEntity.setDateOfLike(likedBy.getDateOfLike());

        return likeEntity;
    }
}
