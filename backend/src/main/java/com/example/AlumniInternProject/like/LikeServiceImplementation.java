package com.example.AlumniInternProject.like;

import com.example.AlumniInternProject.entity.Like;
import com.example.AlumniInternProject.post.PostRepository;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImplementation implements LikeService{

    @Autowired
    private final LikeRepository likeRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostRepository postRepository;

    @Override
    public LikeGetDto save(LikeDto likeDto) {
        var user = userRepository.findById(likeDto.getUserLikes())
                .orElseThrow(RuntimeException::new);
        var post = postRepository.findById(likeDto.getPostLikes())
                .orElseThrow(RuntimeException::new);
        var like = new Like();
        like.setUserLikes(user);
        like.setPostLikes(post);

        return map(likeRepository.save(like));
    }

    @Override
    public List<LikeGetDto> findAll() {
        return likeRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public LikeGetDto findById(UUID id) {
        var like = likeRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        return map(like);
    }

    @Override
    public LikeGetDto update(UUID id, LikeDto likeDto) {
        var like = likeRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        like.setUserLikes(userRepository.findById(likeDto.getUserLikes())
                .orElseThrow(RuntimeException::new));

        like.setPostLikes(postRepository.findById(likeDto.getPostLikes())
                .orElseThrow(RuntimeException::new));

        return map(like);
    }


    private LikeGetDto map(Like like) {
        var likeGetDto = new LikeGetDto();
        likeGetDto.setId(like.getId());
        likeGetDto.setPostLikes(like.getPostLikes().getId());
        likeGetDto.setUserLikes(like.getUserLikes().getId());

        return likeGetDto;
    }
    @Override
    public LikeGetDto addLike(LikeGetDto likeGetDto) {
        Like like = new Like();
        var post = postRepository.findById(likeGetDto.getPostLikes()).get();
        var user = userRepository.findById(likeGetDto.getUserLikes()).get();
        like.setId(likeGetDto.getId());
        like.setUserLikes(user);
        like.setPostLikes(post);

        likeRepository.save(like);

        return map(like);
    }

    @Override
    public void delete(UUID id) {
        if (likeRepository.findById(id).isPresent()){
            likeRepository.deleteById(id);
        }

    }

    @Override
    public Long postLikes(UUID id) {
        return likeRepository.findAll()
                .stream()
                .filter(likeEntity -> likeEntity.getPostLikes().getId().equals(id))
                .map(this::map)
                .count();
    }
}
