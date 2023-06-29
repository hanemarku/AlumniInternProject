package com.example.AlumniInternProject.post;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;




@Service
@RequiredArgsConstructor
public class PostImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public List<PostGetDto> getAllPosts() {
        return  postRepository.findAll()
                .stream()
                .map(post -> map(post))
                .collect(Collectors.toList()
        );
    }

    @Override
    public PostGetDto getPostById(UUID id) {
        var optional = postRepository.findById(id);
        if (optional.isPresent()){
            return map(optional.get());
        }throw new RuntimeException("Posti i kerkuar nuk gjendet!");
    }

    @Override
    public List<PostGetDto> getPostsByUser(UUID user_id) {
        return postRepository.findByUser(user_id);
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
                postDto.getTag()
        );
        var saved = postRepository.save(post);
        return  map(saved);
    }


    @Override
    public PostGetDto updatePost(UUID id, PostDto postDto) {
        var optional = postRepository.findById(id).orElseThrow(RuntimeException::new);
        optional.setUser(postDto.getUser());
        optional.setLike(postDto.getLike());
        optional.setContent(postDto.getContent());
        optional.setDateOfPost(postDto.getDateOfPost());
        optional.setLikesCount(postDto.getLikesCount());
        optional.setCommentsCount(postDto.getCommentsCount());

        var save = postRepository.save(optional);
        return  map(save);
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);

    }

    @Override
    public List<PostGetDto> searchPostsByKeyword(String keyword) {
        return postRepository.findPostsByKeyword(keyword);
    }

    @Override
    public List<PostGetDto> getPostsByCategory(String category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public List<PostGetDto> getPostsByTag(String tag) {
        return postRepository.findByTagContainingIgnoreCase(tag);
    }

    @Override
    public List<PostGetDto> getPostsByDateRange(Date startDate, Date endDate) {

        return null;
    }

    public static PostGetDto map(Post post) {
        var postDTO = new PostGetDto();
        postDTO.setUserId(post.getUser().getId());
        postDTO.setLikeId(post.getLike().getId());
        postDTO.setContent(post.getContent());
        postDTO.setDateOfPost(post.getDateOfPost());
        postDTO.setLikesCount(post.getLikesCount());
        postDTO.setCommentsCount(post.getCommentsCount());
        return postDTO;
    }


}

