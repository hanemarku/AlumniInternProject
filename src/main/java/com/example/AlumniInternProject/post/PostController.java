package com.example.AlumniInternProject.post;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostGetDto> getAllPosts(){
        return postService.getAllPosts();
    }


    @GetMapping("{id}")
    public PostGetDto getPostById(@PathVariable UUID id){
        return postService.getPostById(id);
    }

    @GetMapping("user/{user_id}")
    public List<PostGetDto> getPostsByUser(@PathVariable UUID user_id){
        return postService.getPostsByUser(user_id);
    }

    @PostMapping
    public PostGetDto createPost(@RequestBody PostDto postDto){
        return postService.createPost(postDto);
    }

    @PutMapping("update/{id}")
    public PostGetDto updatePost(@PathVariable UUID id, @RequestBody PostDto postDto){
        return postService.updatePost(id, postDto);
    }


    @DeleteMapping("delete/{id}")
    void deletePost(@PathVariable UUID id){
        postService.deletePost(id);
    }



    @GetMapping("/find/byDate")
    public Optional<Post> findPostByDateRange(@RequestBody Date startDate, @RequestBody Date endDate){
        return postService.findPostByDateRange(startDate, endDate);
    }












}
