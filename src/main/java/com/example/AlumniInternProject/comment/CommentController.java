package com.example.AlumniInternProject.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentGetDto save(@RequestBody CommentDto commentDto){
        return commentService.save(commentDto);
    }

    @GetMapping
    public List<CommentGetDto> findAll(){
        return commentService.findAll();
    }

    @GetMapping("{id}")
    public CommentGetDto findById(@PathVariable UUID id){
        return commentService.findById(id);
    }

    @PatchMapping("{id}")
    public CommentGetDto update(@PathVariable UUID id,@RequestBody CommentDto commentDto){
        return commentService.update(id, commentDto);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable UUID id){
        commentService.delete(id);
    }
}
