package com.example.AlumniInternProject.comment;

import com.example.AlumniInternProject.entity.Comment;
import com.example.AlumniInternProject.post.PostRepository;
import com.example.AlumniInternProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImplementation implements CommentService{

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostRepository postRepository;

    @Override
    public CommentGetDto save(CommentDto commentDto) {
        var user = userRepository.findById(commentDto.getUserComments())
                .orElseThrow(RuntimeException::new);
        var post = postRepository.findById(commentDto.getPostComments())
                .orElseThrow(RuntimeException::new);
        var comment = new Comment();

        comment.setUserComments(user);
        comment.setPostComments(post);
        comment.setContent(commentDto.getCommentContent());

        return map(commentRepository.save(comment));
    }

    @Override
    public List<CommentGetDto> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public CommentGetDto findById(UUID id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        return map(comment);
    }

    @Override
    public CommentGetDto update(UUID id, CommentDto commentDto) {
        var comment = commentRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        comment.setUserComments(userRepository.findById(id)
                .orElseThrow(RuntimeException::new));
        comment.setPostComments(postRepository.findById(id).orElseThrow(RuntimeException::new));
        comment.setContent(commentDto.getCommentContent());

        return map(commentRepository.save(comment));
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);

    }

    @Override
    public List<CommentGetDto> commentsPost(UUID postId) {
        return commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getPostComments().getId().equals(postId)
                        && comment.getPostComments().getAuthor().getId().equals(postId))
                .map(this::map)
                .collect(Collectors.toList());
    }

    private CommentGetDto map(Comment comment){
        var comentDto = new CommentGetDto();
        comment.setId(comment.getId());
        comentDto.setPostComments(comment.getUserComments().getId());
        comentDto.setPostComments(comment.getPostComments().getId());
        comentDto.setCommentContent(comment.getContent());

        return comentDto;
    }
}
