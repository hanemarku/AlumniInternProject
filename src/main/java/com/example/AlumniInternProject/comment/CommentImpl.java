package com.example.AlumniInternProject.comment;

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
    public CommentGetDto save(CommentDto commentDto) {
        var comment = new Comment(
                commentDto.getUser(),
                commentDto.getPost(),
                commentDto.getContent(),
                commentDto.getDateOfCreation()
        );
        var saveComment = commentRepository.save(comment);
        return map(saveComment);
    }

    @Override
    public List<CommentGetDto> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> map(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentGetDto findById(UUID id) {
        var optional = commentRepository.findById(id);
        if (optional.isPresent()){
            return map(optional.get());
        }else throw new RuntimeException("Comment not found");
    }

    @Override
    public CommentGetDto update(UUID id, CommentDto commentDto) {
        var optional = commentRepository.findById(id).orElseThrow(RuntimeException::new);
        optional.setUser(commentDto.getUser());
        optional.setPost(commentDto.getPost());
        optional.setContent(commentDto.getContent());
        optional.setCommmentCreation(commentDto.getDateOfCreation());

        var saveComment = commentRepository.save(optional);
        return map(saveComment);
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }

    private CommentGetDto map(Comment comment){
        var dto = new CommentGetDto();

        dto.setUser(comment.getUser());
        dto.setPost(comment.getPost());
        dto.setContent(comment.getContent());
        dto.setDateOfCreation(comment.getCommmentCreation());

        return dto;
    }
}
