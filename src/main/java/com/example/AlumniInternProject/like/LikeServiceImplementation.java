package com.example.AlumniInternProject.like;


import com.example.AlumniInternProject.comment.CommentConverter;
import com.example.AlumniInternProject.entity.User;
import com.example.AlumniInternProject.post.PostConverter;
import com.example.AlumniInternProject.user.UserConverter;
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
        return likeRepository.findAll()
                .stream()
                .map(LikeConverter::convertLikeEntityToDto)
                .toList();
    }

    @Override
    public LikeDto getLikeById(Integer id) {
        var like = likeRepository.findById(id).orElseThrow();
        return LikeConverter.convertLikeEntityToDto(like);
    }

    @Override
    public LikeDto addLike(LikeDto likeDto) {
        var likeEntity = new LikeEntity();
                likeDto.setId(likeDto.getId());
                likeDto.setUser(likeDto.getUser());
                likeDto.setPost(likeDto.getPost());
                likeDto.setDateOfLike(likeDto.getDateOfLike());
                likeDto.setComment(likeDto.getComment());

        return LikeConverter.convertLikeEntityToDto(likeRepository.save(likeEntity));

    }

    @Override
    public void deleteLike(Integer id) {
        likeRepository.deleteById(id);

    }

    @Override
    public LikeDto updateLike(Integer id, LikeDto likeDto) {
        var like = likeRepository.findById(id).get();

        like.setUser(UserConverter.convertUserDtoToEntity(likeDto.getUser()));
        like.setPost(PostConverter.convertPostDtoToEntity(likeDto.getPost()));
        like.setComment(CommentConverter.convertCommentDtoToEntity(likeDto.getComment()));
        like.setDateOfLike(likeDto.getDateOfLike());

        likeRepository.save(like);
        return LikeConverter.convertLikeEntityToDto(like);

    }
}
