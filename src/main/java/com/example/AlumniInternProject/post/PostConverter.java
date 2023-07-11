package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.Post;
import com.example.AlumniInternProject.like.LikeConverter;
import com.example.AlumniInternProject.user.UserConverter;

public class PostConverter {

    public static PostDto convertPostEntityToDto(Post post) {
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setAuthor(UserConverter.convertUserEntityToDto(post.getAuthor()));
        postDto.setLikedBy(LikeConverter.convertLikeEntityToDto(post.getLikedBy()));
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setTag(post.getTag());
        postDto.setKeyword(post.getKeyword());
        postDto.setDateOfPost(post.getDateOfPost());

        return postDto;
    }

    public static Post convertPostDtoToEntity(PostDto postDto) {
        Post post = new Post();

        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTag(postDto.getTag());
        post.setKeyword(postDto.getKeyword());
        post.setDateOfPost(postDto.getDateOfPost());

        return post;
    }
}
