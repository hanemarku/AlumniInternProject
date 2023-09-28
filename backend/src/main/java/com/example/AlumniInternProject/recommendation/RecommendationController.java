package com.example.AlumniInternProject.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("api/v1/recommendations")
@RequiredArgsConstructor

public class RecommendationController  {
    private final RecommendationService recommendationService;

    @PostMapping
    public RecommendationGetDto save(@RequestBody RecommendationDto dto) {
        return recommendationService.save(dto);
    }

    @GetMapping
    public List<RecommendationGetDto> getAll(){
        return recommendationService.findAll();
    }

    @GetMapping("{id}")
    public RecommendationGetDto findById(@PathVariable UUID id){
        return recommendationService.findById(id);
    }

    @PatchMapping("{id}")
    public RecommendationGetDto update(@PathVariable UUID id, @RequestBody RecommendationDto dto){
        return recommendationService.update(id, dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        recommendationService.delete(id);
    }

    @GetMapping("recommender/{recommenderId}")
    public RecommendationGetDto getRecommendationByRecommenderID(@PathVariable UUID recommenderId){
        return recommendationService.getRecommendationByRecommenderID(recommenderId);
    }

    @GetMapping("recommendedUser/{recommendedUserId}")
    public RecommendationGetDto getRecommendationByRecommendedUserID(@PathVariable UUID recommendedUserId){
        return recommendationService.getRecommendationByRecommendedUserID(recommendedUserId);
    }



}
