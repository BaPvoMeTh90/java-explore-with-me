package ru.practicum.comment.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.base.model.State;
import ru.practicum.comment.dto.AdminCommentOutputDto;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.UserCommentOutputDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.events.model.Event;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static AdminCommentOutputDto commentToAdminCommentOutputDto(Comment comment, UserShortDto user) {
        return new AdminCommentOutputDto(
                comment.getId(),
                comment.getText(),
                comment.getEvent().getId(),
                user,
                comment.getCreatedOn(),
                comment.getUpdatedOn(),
                comment.getState()
        );
    }

    public static Comment commentInputDtoToComment(CommentInputDto comment, User user, Event event) {
        return new Comment(
                0L,
                comment.getText(),
                event,
                user,
                LocalDateTime.now(),
                LocalDateTime.now(),
                State.PENDING
        );
    }

    public static UserCommentOutputDto commentToUserCommentOutputDto(Comment comment, UserShortDto user) {
        return new UserCommentOutputDto(
                comment.getId(),
                comment.getText(),
                user,
                comment.getCreatedOn(),
                comment.getUpdatedOn()
        );
    }
}
