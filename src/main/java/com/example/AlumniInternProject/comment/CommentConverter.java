package com.example.AlumniInternProject.comment;

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

    public static Comment convertCommentDtoToEntity(CommentDto commentDto){
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setUser(commentDto.getUser());
        comment.setPost(commentDto.getPost());
        comment.setContent(commentDto.getContent());
        comment.setCommmentCreation(commentDto.getDateOfCreation());

        return comment;
    }
}
