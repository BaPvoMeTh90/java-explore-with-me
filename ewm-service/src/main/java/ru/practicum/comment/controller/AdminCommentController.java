package ru.practicum.comment.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.base.model.State;
import ru.practicum.comment.dto.AdminCommentOutputDto;
import ru.practicum.comment.dto.AdminStatusUpdateCommentInputDto;
import ru.practicum.comment.service.AdminCommentService;

import java.util.List;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
@Slf4j
public class AdminCommentController {
    private final AdminCommentService commentService;

    @GetMapping("/users/{authorId}")
    public ResponseEntity<List<AdminCommentOutputDto>> getAuthorComments(@PathVariable @Positive Long authorId,
                                                                         @RequestParam(defaultValue = "0") int from,
                                                                         @RequestParam(defaultValue = "10") int size) {
        log.info("Admin: Получение комментариев пользователя с идентификатором {}", authorId);
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        List<AdminCommentOutputDto> comments = commentService.getAuthorComments(authorId, pageRequest);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<AdminCommentOutputDto> adminUpdateStatus(@PathVariable @Positive Long commentId,
                                                                   @RequestBody AdminStatusUpdateCommentInputDto adminStatusUpdateCommentInputDto) {
        log.info("Admin: обновлен статус на {}, комментария с идентификатором {}", adminStatusUpdateCommentInputDto, commentId);
        AdminCommentOutputDto adminCommentDto = commentService.adminUpdateCommentStatus(commentId, adminStatusUpdateCommentInputDto);
        return new ResponseEntity<>(adminCommentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> adminDeleteComment(@PathVariable @Positive Long commentId) {
        log.info("Admin: удален комментарий с идентификатором {}", commentId);
        commentService.adminDeleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/state")
    public AdminStatusUpdateCommentInputDto getState() {
        AdminStatusUpdateCommentInputDto adminStatusUpdateCommentInputDto = new AdminStatusUpdateCommentInputDto();
        adminStatusUpdateCommentInputDto.setState(State.PUBLISHED);

        return adminStatusUpdateCommentInputDto;
    }
}
