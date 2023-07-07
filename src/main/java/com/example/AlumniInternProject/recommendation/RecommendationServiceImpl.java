//package com.example.AlumniInternProject.recommendation;
//
//import com.example.AlumniInternProject.entity.Recommendation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RecommendationServiceImpl implements RecommendationService{
//    private final RecommendationRepository recommendationRepository;
//
//    public RecommendationGetDto save(RecommendationDto recommendationDto) {
//        var recommendation = new Recommendation(
//                recommendationDto.getRecommender(),
//                recommendationDto.getRecommendedUser(),
//                recommendationDto.getComment(),
//                recommendationDto.getTimestamp()
//        );
//        var saved = recommendationRepository.save(recommendation);
//        return map(saved);
//    }
//
//    private RecommendationGetDto map(Recommendation recommendation){
//        var dto = new RecommendationGetDto();
//        dto.setId(recommendation.getId());
//        dto.setRecommender(recommendation.getRecommender());
//        dto.setRecommendedUser(recommendation.getRecommendedUser());
//        dto.setComment(recommendation.getComment());
//        dto.setTimestamp(recommendation.getTimestamp());
//        return dto;
//    }
//}
