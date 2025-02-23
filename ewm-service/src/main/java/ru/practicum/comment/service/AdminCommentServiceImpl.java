package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.base.model.State;
import ru.practicum.comment.dto.AdminCommentOutputDto;
import ru.practicum.comment.dto.AdminStatusUpdateCommentInputDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void adminDeleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id: " + commentId + " не найден."));
        commentRepository.deleteById(commentId);
    }

    @Override
    public AdminCommentOutputDto adminUpdateCommentStatus(Long commentId,
                                                          AdminStatusUpdateCommentInputDto adminStatusUpdateCommentInputDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден."));
        State state = adminStatusUpdateCommentInputDto.getState();
        if (state != null) {
            switch (state) {
                case PENDING -> comment.setState(State.PENDING);
                case PUBLISHED -> comment.setState(State.PUBLISHED);
                case CANCELED -> comment.setState(State.CANCELED);
                default -> throw new ValidationException("Статус не существует - " + state);
            }
        }
        commentRepository.save(comment);
        return CommentMapper.commentToAdminCommentOutputDto(comment, UserMapper.userToUserShortDto(comment.getAuthor()));
    }

    @Override
    public List<AdminCommentOutputDto> getAuthorComments(Long authorId, PageRequest pageRequest) {
        userRepository.findById(authorId).orElseThrow(()
                -> new NotFoundException("Пользователь с id \"" + authorId + "\" не найден"));
        List<Comment> comments = commentRepository.findByAuthorId(authorId, pageRequest);
        return comments.stream().map((Comment comment) -> CommentMapper.commentToAdminCommentOutputDto(comment,
                UserMapper.userToUserShortDto(comment.getAuthor()))).toList();
    }
}
