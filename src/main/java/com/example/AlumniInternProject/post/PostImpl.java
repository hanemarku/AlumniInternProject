package com.example.AlumniInternProject.post;


import com.example.AlumniInternProject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;





@Service
@RequiredArgsConstructor
public class PostImpl implements PostService {

    PostRepository postRepository;

    @Override
    public List<PostGetDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> map(post))
                .collect(Collectors.toList());
    }

    /*private PostGetDto map(Post post) {
        //make a mapper
        var mapedPost = new PostGetDto();
        mapedPost.setUser(post.getUser());
        mapedPost.setLike(post.getLike());
        mapedPost.setContent(post.getContent());
        mapedPost.setDateOfPost(post.getDateOfPost());
        mapedPost.setLikesCount(post.getLikesCount());
        mapedPost.setCommentsCount(post.getCommentsCount());
        mapedPost.setKeyword(post.getKeyword());
        mapedPost.setCategory(post.getCategory());
        mapedPost.setTag(post.getTag());
        return mapedPost;
    }*/


    @Override
    public PostGetDto getPostById(UUID id) {
        var optional = postRepository.findById(id);
        if (optional.isPresent()) {
            return map(optional.get());
        }
        throw new RuntimeException("Posti me kete id nuk ekziston!");
    }

    @Override
    public List<PostGetDto> getPostsByUser(UUID userId) {
        var optional = postRepository.findById(userId);
        if (optional.isPresent()) {
            return (List<PostGetDto>) map(optional.get());
        }
        throw new RuntimeException("Posti me kete id nuk ekziston!");
    }

    @Override
    public PostGetDto createPost(PostDto postDto) {
        var post = new Post(
                postDto.getUser(),
                postDto.getLike(),
                postDto.getContent(),
                postDto.getDateOfPost(),
                postDto.getLikesCount(),
                postDto.getCommentsCount(),
                postDto.getKeyword(),
                postDto.getCategory(),
                postDto.getTag(),
                postDto.getSharedUsers(),
                postDto.getSharingUser()
        );
        var savePost = postRepository.save(post);
        return map(savePost);
    }

    @Override
    public PostGetDto updatePost(UUID id, PostDto postDto) {
        var optional = postRepository.findById(id).orElseThrow(RuntimeException::new);
        optional.setUser(postDto.getUser());
        optional.setTag(postDto.getTag());
        optional.setCategory(postDto.getCategory());
        optional.setDateOfPost(postDto.getDateOfPost());
        optional.setKeyword(postDto.getKeyword());
        optional.setLike(postDto.getLike());
        optional.setContent(postDto.getContent());

        var savePost = postRepository.save(optional);
        return map(savePost);
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);

    }

    @Override
    public Optional<Post> findPostByDateRange(Date startDate, Date endDate) {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            Date postDate = post.getDateOfPost(); // Assuming the post object has a method to get its date

            // Check if the post date is within the specified range
            if (postDate.after(startDate) && postDate.before(endDate)) {
                    return Optional.of(post);
            }
        }

        // If no post is found within the range or with the specified date, return an empty Optional
        return Optional.empty();
    }

    @Override
    public void addLikeToPost(UUID postId, UUID likeId) {
        var optional = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        optional.setLikeId(likeId);
        var savePost = postRepository.save(optional);
        map(savePost);
    }

    @Override
    public void removeLikeFromPost(UUID postId, UUID likeId) {
        var optional = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        optional.setLikeId(null);
        var savePost = postRepository.save(optional);
        map(savePost);
    }

    @Override
    public void sharePost(UUID postId, User sharingUser, List<User> sharedUsers) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Add the sharingUser to the post's sharedByUsers collection
            post.getSharedByUsers().add(sharingUser);

            // Add the targetUsers to the post's sharedWithUsers collection
            post.getSharedWithUsers().addAll(sharedUsers);

            postRepository.save(post);
        } else {
            // Handle post not found error
            throw new IllegalArgumentException("Post not found");
        }
    }

    PostMapper postMapper;

    public PostGetDto map(Post post) {
        return postMapper.postToPostGetDto(post);
    }

}



