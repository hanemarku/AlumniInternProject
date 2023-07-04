package com.example.AlumniInternProject.like;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/likes")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public LikeGetDto saveLike(@RequestBody LikeDto likeDto){
        return likeService.save(likeDto);
    }

    @GetMapping
    public List<LikeGetDto> findAllLikes(){
        return likeService.findAll();
    }

    @GetMapping("{id}")
    public LikeGetDto findLikeById(@PathVariable UUID id){
        return likeService.findById(id);
    }

    @PatchMapping("{id}")
    public LikeGetDto updateLike(@PathVariable UUID id, @RequestBody LikeDto likeDto){
        return likeService.update(id, likeDto);
    }

    @DeleteMapping("{id}")
    void deleteLike(@PathVariable UUID id){
        likeService.delete(id);
    }
}
