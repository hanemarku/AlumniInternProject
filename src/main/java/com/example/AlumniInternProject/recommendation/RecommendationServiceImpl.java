package com.example.AlumniInternProject.recommendation;

import com.example.AlumniInternProject.entity.Recommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService{
    private final RecommendationRepository recommendationRepository;

    @Override
    public RecommendationGetDto save(RecommendationDto recommendationDto) {
        var recommendation = new Recommendation(
                recommendationDto.getRecommender(),
                recommendationDto.getRecommendedUser(),
                recommendationDto.getComment(),
                recommendationDto.getTimestamp()
        );
        var saved = recommendationRepository.save(recommendation);
        return map(saved);
    }

    @Override
    public List<RecommendationGetDto> findAll(){
        return recommendationRepository.findAll()
                .stream()
                .map(recommendation -> map(recommendation))
                .collect(Collectors.toList());
    }

    @Override
    public RecommendationGetDto findById(UUID id){
        var optional = recommendationRepository.findById(id);
        if (optional.isPresent()){
            return map(optional.get());
        }
        throw new RuntimeException("Recommendation not found");
    }

    @Override
    public RecommendationGetDto update(UUID id, RecommendationDto dto){
        var skill = recommendationRepository.findById(id).orElseThrow(RuntimeException::new);
        skill.setComment(dto.getComment());
        var saved = recommendationRepository.save(skill);
        return map(saved);
    }

    @Override
    public void delete(UUID id) {
        recommendationRepository.deleteById(id);
    }

    private RecommendationGetDto map(Recommendation recommendation){
        var dto = new RecommendationGetDto();
        dto.setId(recommendation.getId());
        dto.setRecommender(recommendation.getRecommender().);
        dto.setRecommendedUser(recommendation.getRecommendedUser());
        dto.setComment(recommendation.getComment());
        dto.setTimestamp(recommendation.getTimestamp());
        return dto;
    }
}
