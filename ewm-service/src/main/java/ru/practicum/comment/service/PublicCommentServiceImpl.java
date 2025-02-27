package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.base.model.State;
import ru.practicum.comment.dto.UserCommentOutputDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ViolationException;
import ru.practicum.user.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<UserCommentOutputDto> getAllEventComments(Long eventId, PageRequest pageRequest) {
        checkEvent(eventId);
        List<Comment> comments = commentRepository
                .findAllByEventIdAndState(eventId, State.PUBLISHED, pageRequest);
        return comments.stream().map((Comment comment) ->
                CommentMapper.commentToUserCommentOutputDto(comment, UserMapper.userToUserShortDto(comment.getAuthor()))).toList();
    }

    @Override
    public UserCommentOutputDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id: " + commentId + " не найден"));
        if (comment.getState() != State.PUBLISHED) {
            throw new ViolationException("Комментарий не опубликован");
        }
        checkEvent(comment.getEvent().getId());
        return CommentMapper.commentToUserCommentOutputDto(comment, UserMapper.userToUserShortDto(comment.getAuthor()));
    }

    private void checkEvent(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id: " + eventId + " не найдено"));
    }
}
