package com.example.AlumniInternProject.recommendation;

import com.example.AlumniInternProject.entity.Recommendation;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService{
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    @Override
    public RecommendationGetDto save(RecommendationDto recommendationDto) {
        User recommendedUser = userRepository.findById(recommendationDto.getRecommendedUser())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + (recommendationDto.getRecommendedUser()) + " was not found"));

        User recommender = userRepository.findById(recommendationDto.getRecommender())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + (recommendationDto.getRecommender()) + " was not found"));
        var recommendation = new Recommendation(
                recommender,
                recommendedUser,
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
        var recommendation = recommendationRepository.findById(id).orElseThrow(RuntimeException::new);
        recommendation.setComment(dto.getComment());
        var saved = recommendationRepository.save(recommendation);
        return map(saved);
    }

    @Override
    public void delete(UUID id) {
        recommendationRepository.deleteById(id);
    }

    @Override
    public RecommendationGetDto getRecommendationByRecommenderID(UUID recommenderId){
        Optional<Recommendation> optional = recommendationRepository.findByRecommenderId(recommenderId);
        if (optional.isPresent()){
            return map(optional.get());
        }
        throw new RuntimeException("Recommendation not found");
    }

    @Override
    public RecommendationGetDto getRecommendationByRecommendedUserID(UUID recommendedUserId){
        Optional<Recommendation> optional = recommendationRepository.findByRecommendedUserId(recommendedUserId);
        if (optional.isPresent()){
            return map(optional.get());
        }
        throw new RuntimeException("Recommendation not found");
    }

    private RecommendationGetDto map(Recommendation recommendation){
        var dto = new RecommendationGetDto();
        dto.setId(recommendation.getId());
        dto.setRecommender(recommendation.getRecommender().getId());
        dto.setRecommendedUser(recommendation.getRecommendedUser().getId());
        dto.setComment(recommendation.getComment());
        dto.setTimestamp(recommendation.getTimestamp());
        return dto;
    }
}
