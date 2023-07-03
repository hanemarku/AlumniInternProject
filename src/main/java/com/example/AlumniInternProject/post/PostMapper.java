package com.example.AlumniInternProject.post;

import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {

    PostGetDto postToPostGetDto(Post post);
}
