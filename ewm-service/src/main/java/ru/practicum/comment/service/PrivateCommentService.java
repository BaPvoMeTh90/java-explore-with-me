package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.UserCommentOutputDto;
import ru.practicum.comment.dto.UserUpdateCommentInputDto;

public interface PrivateCommentService {
    UserCommentOutputDto userAddComment(CommentInputDto commentInputDto, Long authorId);

    UserCommentOutputDto userUpdateComment(UserUpdateCommentInputDto userUpdateCommentInputDto,
                                           Long authorId, Long commentId);

    void userDeleteComment(Long authorId, Long commentId);
}
