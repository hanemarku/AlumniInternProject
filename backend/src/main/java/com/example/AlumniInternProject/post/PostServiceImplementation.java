package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.comment.CommentRepository;
import com.example.AlumniInternProject.entity.Comment;
import com.example.AlumniInternProject.entity.Post;
import com.example.AlumniInternProject.like.LikeRepository;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService{

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final LikeRepository likeRepository;

    @Override
    public PostGetDto save(PostDto postDto) {
        var user = userRepository.findById(postDto.getAuthor())
                .orElseThrow(RuntimeException::new);

        var comment = commentRepository.findAllById(postDto.getComment());

        var like = likeRepository.findAllById(postDto.getLike());

        var post = new Post();

        post.setAuthor(user);
        post.setComments(comment);
        post.setContent(postDto.getContent());
        post.setLikes(like);
        post.setPostCreation(postDto.getPostCreation());
        post.setProfilePicUrl(postDto.getProfilePicUrl());

        return map(postRepository.save(post));
    }

    @Override
    public List<PostGetDto> findPostsByUser(UUID id) {
        return postRepository.findAll().stream()
                .filter(post -> post.getAuthor().getId().equals(id))
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostGetDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public PostGetDto findById(UUID id) {
        var post = postRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        return map(post);
    }

    @Override
    public PostGetDto update(UUID id, PostDto postDto) {
        var post = postRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        post.setAuthor(userRepository.findById(postDto.getAuthor())
                .orElseThrow(RuntimeException::new));
        post.setLikes(likeRepository.findAllById(postDto.getLike()));
        post.setComments(commentRepository.findAllById(postDto.getComment()));
        post.setContent(postDto.getContent());
        post.setPostCreation(postDto.getPostCreation());
        post.setProfilePicUrl(postDto.getProfilePicUrl());

        return map(postRepository.save(post));
    }

    @Override
    public void delete(UUID id) {
        postRepository.deleteById(id);

    }

    private PostGetDto map(Post post){
        var dto = new PostGetDto();
        dto.setId(post.getId());
        dto.setAuthor(post.getAuthor().getId());
        dto.setLike(post.getLikes().stream().map(like -> like.getUserLikes().getId()).toList());
        dto.setComment(post.getComments().stream().map(Comment::getId).toList());
        dto.setContent(post.getContent());
        dto.setPostCreation(post.getPostCreation());
        dto.setProfilePicUrl(post.getProfilePicUrl());

        return dto;
    }
}
