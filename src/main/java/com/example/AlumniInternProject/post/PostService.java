package com.example.AlumniInternProject.post;

import com.example.AlumniInternProject.entity.User;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {

    List<PostGetDto> getAllPosts();

    PostGetDto getPostById(UUID id);

    List<PostGetDto> getPostsByUser(UUID userId);

    PostGetDto createPost(PostDto postDto);

    PostGetDto updatePost(UUID id, PostDto postDto);

    void deletePost(UUID id);

    Optional<Post> findPostByDateRange(Date startDate, Date endDate);

    void addLikeToPost(UUID postId, UUID likeId);

    void removeLikeFromPost(UUID postId, UUID likeId);

    void sharePost(UUID postId, User sharingUser, List<User> sharedUsers);

}

