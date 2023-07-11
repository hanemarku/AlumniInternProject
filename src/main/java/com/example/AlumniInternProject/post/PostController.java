package com.example.AlumniInternProject.post;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/posts")
//@RequiredArgsConstructor
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDto> findAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("{id}")
    public PostDto findPostById(@PathVariable Integer id){
        return postService.getPostById(id);
    }

    @PostMapping
    public PostDto savePost(@RequestBody PostDto postDto){
        return postService.createPost(postDto);
    }

    @PutMapping("{id}")
    public PostDto updatePost(@PathVariable Integer id, @RequestBody PostDto postDto){
        return postService.updatePost(id, postDto);
    }

    @DeleteMapping("{id}")
    void deletePost(@PathVariable Integer id){
        postService.deletePost(id);
    }

}
