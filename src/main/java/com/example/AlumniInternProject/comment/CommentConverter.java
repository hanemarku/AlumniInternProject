package com.example.AlumniInternProject.comment;

import com.example.AlumniInternProject.entity.Comment;

public class CommentConverter {

    public static CommentDto convertCommentToDto(Comment comment){
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setUser(comment.getUser());
        commentDto.setPost(comment.getPost());
        commentDto.setContent(comment.getContent());
        commentDto.setDateOfCreation(comment.getCommmentCreation());

        return commentDto;
    }
}
