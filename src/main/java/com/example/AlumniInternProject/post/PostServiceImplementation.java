package com.example.AlumniInternProject.post;


import com.example.AlumniInternProject.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j // this is for logging
public class PostServiceImplementation implements PostService{


    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto getPostById(Integer id) {
        var postEntity = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return PostConverter.convertPostEntityToDto(postEntity);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostConverter::convertPostEntityToDto)
                .toList();
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTag(postDto.getTag());
        post.setKeyword(postDto.getKeyword());
        post.setDateOfPost(postDto.getDateOfPost());

        postRepository.save(post);
        return PostConverter.convertPostEntityToDto(post);

    }

    @Override
    public PostDto updatePost(Integer id, PostDto postDto) {
        var optional = postRepository.findById(id).get();

        optional.setTitle(postDto.getTitle());
        optional.setContent(postDto.getContent());
        optional.setTag(postDto.getTag());
        optional.setKeyword(postDto.getKeyword());
        optional.setDateOfPost(postDto.getDateOfPost());
        postRepository.save(optional);
        return PostConverter.convertPostEntityToDto(optional);
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }
}
