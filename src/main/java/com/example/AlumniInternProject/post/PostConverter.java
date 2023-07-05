package com.example.AlumniInternProject.post;

public class PostConverter {

    public static PostDto convertPostEntityToDto(PostEntity postEntity) {
        PostDto postDto = new PostDto();

        postDto.setId(postEntity.getId());
        postDto.setAuthor(UserConverter.convertUserEntityToDto(postEntity.getAuthor()));
        postDto.setLikedBy(LikeConverter.convertLikeEntityToDto(postEntity.getLikedBy()));
        postDto.setTitle(postEntity.getTitle());
        postDto.setContent(postEntity.getContent());
        postDto.setTag(postEntity.getTag());
        postDto.setKeyword(postEntity.getKeyword());
        postDto.setDateOfPost(postEntity.getDateOfPost());

        return postDto;
    }
}
