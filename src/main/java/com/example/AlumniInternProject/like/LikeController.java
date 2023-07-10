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
    public LikeDto saveLike(@RequestBody LikeDto likeDto){
        return likeService.addLike(likeDto);
    }

    @GetMapping
    public List<LikeDto> findAllLikes(){
        return likeService.getAllLikes();
    }

    @GetMapping("{id}")
    public LikeDto findLikeById(@PathVariable Integer id){
        return likeService.getLikeById(id);
    }

    @PatchMapping("{id}")
    public LikeDto updateLike(@PathVariable Integer id, @RequestBody LikeDto likeDto){
        return likeService.updateLike(id, likeDto);
    }

    @DeleteMapping("{id}")
    void deleteLike(@PathVariable Integer id){
        likeService.deleteLike(id);
    }
}
