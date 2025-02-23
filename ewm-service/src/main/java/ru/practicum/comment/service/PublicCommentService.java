package ru.practicum.comment.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.comment.dto.UserCommentOutputDto;

import java.util.List;

public interface PublicCommentService {
    List<UserCommentOutputDto> getAllEventComments(Long eventId, PageRequest pageRequest);

    UserCommentOutputDto getCommentById(Long commentId);
}
