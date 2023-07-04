package com.example.AlumniInternProject.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeImpl implements LikeService{

    private final LikeRepository likeRepository;

    @Override
    public LikeGetDto save(LikeDto likeDto) {
        var like = new Like(
                likeDto.getUser(),
                likeDto.getPost(),
                likeDto.getComment(),
                likeDto.getDateOfLike()
        );

        var saveLike = likeRepository.save(like);
        return map(saveLike);
    }

    @Override
    public List<LikeGetDto> findAll() {
        return likeRepository.findAll()
                .stream()
                .map(like -> map(like))
                .collect(Collectors.toList());
    }

    @Override
    public LikeGetDto findById(UUID id) {
        var optional = likeRepository.findById(id);
        if (optional.isPresent()){
            return map(optional.get());
        }throw new RuntimeException("Like does not exists");
    }

    @Override
    public LikeGetDto update(UUID id, LikeDto likeDto) {
        var optional = likeRepository.findById(id).orElseThrow(RuntimeException::new);
        optional.setUser(likeDto.getUser());
        optional.setPost(likeDto.getPost());
        optional.setComment(likeDto.getComment());
        optional.setDateOfLike(likeDto.getDateOfLike());

        var saveLike = likeRepository.save(optional);
        return map(saveLike);
    }

    @Override
    public void delete(UUID id) {
        likeRepository.deleteById(id);
    }

    private LikeGetDto map(Like like){
        var dto = new LikeGetDto();

        dto.setUser(like.getUser());
        dto.setPost(like.getPost());
        dto.setComment(like.getComment());
        dto.setDateOfLike(like.getDateOfLike());

        return dto;
    }

    @Override
    public Optional<Like> findLikeByPost(UUID post_id) {
        return likeRepository.findById(post_id);
    }
}
