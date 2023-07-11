package com.example.AlumniInternProject.like;


import com.example.AlumniInternProject.entity.Like;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImplementation implements LikeService{

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public List<LikeDto> getAllLikes() {
        return likeRepository.findAll().stream().map(LikeConverter::convertLikeEntityToDto).toList();
    }

    @Override
    public Optional<LikeDto> getLikeById(Integer id) {
        return likeRepository.findById(id).map(LikeConverter::convertLikeEntityToDto);
    }

    @Override
    public LikeDto addLike(LikeDto likeDto) {
        var likeEntity = new Like();
                likeDto.setId(likeEntity.getId());
                likeDto.setUser(likeEntity.getUser());
                likeDto.setPost(likeEntity.getPost());
                likeDto.setDateOfLike(likeEntity.getDateOfLike());
                likeDto.setComment(likeEntity.getComment());

        return LikeConverter.convertLikeEntityToDto(likeRepository.save(likeEntity));

    }

    @Override
    public void deleteLike(Integer id) {
        likeRepository.deleteById(id);

    }

    @Override
    public LikeDto updateLike(Integer id, LikeDto likeDto) {
        var like = likeRepository.findById(id).orElseThrow(RuntimeException::new);

        like.setUser(likeDto.getUser());
        like.setPost(likeDto.getPost());
        like.setComment(likeDto.getComment());
        like.setDateOfLike(likeDto.getDateOfLike());
        return LikeConverter.convertLikeEntityToDto(likeRepository.save(like));

    }
}
