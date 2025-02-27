package ru.practicum.comment.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.comment.dto.AdminCommentOutputDto;
import ru.practicum.comment.dto.AdminStatusUpdateCommentInputDto;

import java.util.List;

public interface AdminCommentService {
    void adminDeleteComment(Long commentId);

    AdminCommentOutputDto adminUpdateCommentStatus(Long commentId,
                                                   AdminStatusUpdateCommentInputDto adminStatusUpdateCommentInputDto);

    List<AdminCommentOutputDto> getAuthorComments(Long authorId, PageRequest pageRequest);
}
