package com.example.AlumniInternProject.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("user/{id}")
    public List<PostGetDto> getPostsByUser(@PathVariable @RequestBody UUID id){
        return postService.findPostsByUser(id);
    }

    @GetMapping("getpost/{id}")
    public PostGetDto getPost(@PathVariable @RequestBody UUID id){
        return postService.findById(id);
    }

    @PostMapping("cratePost")
    public PostGetDto createPost(@RequestBody PostDto postDto){
        return postService.save(postDto);
    }

    @PutMapping("update/{id}")
    public PostGetDto updatePost(@PathVariable UUID id, @RequestBody PostDto postDto){
        return postService.update(id, postDto);
    }

    @DeleteMapping("delete/{id}")
    void delet(UUID id){
        postService.delete(id);
    }
}
