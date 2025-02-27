package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.UserCommentOutputDto;
import ru.practicum.comment.dto.UserUpdateCommentInputDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.base.model.State;
import ru.practicum.events.model.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exception.ViolationException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public UserCommentOutputDto userAddComment(CommentInputDto commentInputDto, Long authorId) {
        User author = checkUser(authorId);
        Long eventId = commentInputDto.getEventId();
        Event event = checkEvent(eventId);
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException("Событие с id " + eventId + " не опубликовано");
        }
        Comment comment = commentRepository.save(CommentMapper.commentInputDtoToComment(commentInputDto, author, event));
        return CommentMapper.commentToUserCommentOutputDto(comment, UserMapper.userToUserShortDto(author));
    }

    @Override
    public UserCommentOutputDto userUpdateComment(UserUpdateCommentInputDto userUpdateCommentInputDto, Long authorId,
                                                  Long commentId) {
        checkUser(authorId);
        Comment comment = checkComment(commentId);
        if (!authorId.equals(comment.getAuthor().getId()))
            throw new ViolationException("Только создатель комментария может его изменять");
        Long eventId = comment.getEvent().getId();
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не найдено"));
        if (userUpdateCommentInputDto.getText() != null) {
            comment.setText(userUpdateCommentInputDto.getText());
        }
        comment.setUpdatedOn(LocalDateTime.now());
        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.commentToUserCommentOutputDto(updatedComment,
                UserMapper.userToUserShortDto(updatedComment.getAuthor()));
    }

    @Override
    public void userDeleteComment(Long authorId, Long commentId) {
        checkUser(authorId);
        Comment comment = checkComment(commentId);
        if (!authorId.equals(comment.getAuthor().getId()))
            throw new ViolationException("Только создатель комментария может его удалить");
        commentRepository.deleteById(commentId);
    }

    private User checkUser(Long authorId) {
        return userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id: " + authorId + " не найден"));
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id: " + commentId + " не найден"));
    }

    private Event checkEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не найдено"));
    }
}
