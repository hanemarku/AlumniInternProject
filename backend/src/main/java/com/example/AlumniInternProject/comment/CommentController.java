package com.example.AlumniInternProject.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public CommentGetDto createComment(@RequestBody CommentGetDto commentDto){
        return commentService.save(commentDto);
    }

    @PutMapping("updating/{id}")
    public CommentGetDto update(@PathVariable UUID id, @RequestBody CommentGetDto commentGetDto){
        return commentService.update(id, commentGetDto);
    }

    @GetMapping("/{postId}")
    public List<CommentGetDto> commentsPost(@PathVariable UUID postId){
        return commentService.commentsPost(postId);
    }

    @DeleteMapping("{commentId}")
    void delete(@PathVariable UUID commentId){
        commentService.delete(commentId);
    }
}
