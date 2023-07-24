package com.example.AlumniInternProject.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/likes")
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping
    public LikeDto addLike(@RequestBody LikeGetDto likeDto){
        return likeService.addLike(likeDto);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable UUID id){
        likeService.delete(id);
    }

    @GetMapping("{id}")
    public Long postLikes(@PathVariable @RequestBody UUID id){
        return likeService.postLikes(id);
    }
}
