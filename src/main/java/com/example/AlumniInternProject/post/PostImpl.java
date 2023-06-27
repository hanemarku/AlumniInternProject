package com.example.AlumniInternProject.post;


import com.example.AlumniInternProject.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        List<PostGetDto> collect = postRepository.findAll(
                .stream()
                .map(post -> map(post))
                .collect(Collectors.toList()
        );
    }

    @Override
    public PostGetDto getPostById(UUID id) {
        var optional = postRepository.findAllById(Collections.singleton(id));
        if (!(optional.isEmpty())){
            return ModelMapper.map(optional.get());
        }throw new RuntimeException("Posti i kerkuar nuk gjendet!");
    }

    @Override
    public List<PostDto> getPostsByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public PostGetDto createPost(PostDto postDto) {
        var post = new Post(
                postDto.getUser(),
                postDto.getLike(),
                postDto.getContent(),
                postDto.getDateOfPost(),
                postDto.getLikesCount(),
                postDto.getCommentsCount()
        );
        var saved = postRepository.save(post);
        return map(saved);
    }

    //private PostGetDto map(Post saved) {
    //}

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
        return map(save);
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);

    }

    @Override
    public List<PostDto> searchPostsByKeyword(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<PostDto> getPostsByCategory(String category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public List<PostDto> getPostsByTag(String tag) {
        return postRepository.findByTagsContainingIgnoreCase(tag);
    }

    @Override
    public List<PostDto> getPostsByDateRange(Date startDate, Date endDate) {
        return null;
    }


}
