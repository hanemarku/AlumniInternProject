package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.like.LikeConverter;
import com.example.AlumniInternProject.like.LikeEntity;
import com.example.AlumniInternProject.user.UserConverter;

public class PostConverter {

    public static PostDto convertPostEntityToDto(PostEntity postEntity) {
        PostDto postDto = new PostDto();

        postDto.setId(postEntity.getId());
        postDto.setAuthor(UserConverter.convertUserEntityToDto(postEntity.getAuthor()));
        postDto.setLikedBy(LikeConverter.convertLikeEntityToDto((LikeEntity) postEntity.getLikedBy()));
        postDto.setTitle(postEntity.getTitle());
        postDto.setContent(postEntity.getContent());
        postDto.setTag(postEntity.getTag());
        postDto.setKeyword(postEntity.getKeyword());
        postDto.setDateOfPost(postEntity.getDateOfPost());

        return postDto;
    }

    public static PostEntity convertPostDtoToEntity(PostDto postDto) {
        PostEntity postEntity = new PostEntity();

        postEntity.setId(postDto.getId());
        postEntity.setTitle(postDto.getTitle());
        postEntity.setContent(postDto.getContent());
        postEntity.setTag(postDto.getTag());
        postEntity.setKeyword(postDto.getKeyword());
        postEntity.setDateOfPost(postDto.getDateOfPost());

        return postEntity;
    }
}
