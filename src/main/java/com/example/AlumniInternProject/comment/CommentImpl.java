package com.example.AlumniInternProject.comment;

import com.example.AlumniInternProject.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Override
    public CommentDto save(CommentDto commentDto) {
        var comment = new Comment();
        commentDto.setUser(comment.getUser());
        commentDto.setPost(comment.getPost());
        commentDto.setContent(comment.getContent());
        commentDto.setDateOfCreation(comment.getCommmentCreation());

        return CommentConverter.convertCommentToDto(comment);
    }

    @Override
    public List<CommentDto> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(CommentConverter::convertCommentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto findById(UUID id) {
        var optional = commentRepository.findById(id).get();
        return CommentConverter.convertCommentToDto(optional);
    }

    @Override
    public CommentDto update(UUID id, CommentDto commentDto) {
        var optional = commentRepository.findById(id).orElseThrow(RuntimeException::new);
        optional.setUser(commentDto.getUser());
        optional.setPost(commentDto.getPost());
        optional.setContent(commentDto.getContent());
        optional.setCommmentCreation(commentDto.getDateOfCreation());


        return CommentConverter.convertCommentToDto(optional);
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }

}
