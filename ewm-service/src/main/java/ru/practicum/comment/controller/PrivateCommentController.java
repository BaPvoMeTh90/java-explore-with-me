package ru.practicum.comment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.UserCommentOutputDto;
import ru.practicum.comment.dto.UserUpdateCommentInputDto;
import ru.practicum.comment.service.PrivateCommentService;

@RestController
@RequestMapping("/users/{authorId}/comments")
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentController {
    private final PrivateCommentService commentService;

    @PostMapping
    public ResponseEntity<UserCommentOutputDto> addComment(@RequestBody @Valid CommentInputDto commentInputDto,
                                                           @PathVariable @Positive Long authorId) {
        log.info("Получен запрос на добавление комментария событию {} от автора {}", commentInputDto, authorId);
        UserCommentOutputDto comment = commentService.userAddComment(commentInputDto, authorId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<UserCommentOutputDto> updateComment(@RequestBody @Valid UserUpdateCommentInputDto userUpdateCommentInputDto,
                                                        @PathVariable @Positive Long authorId,
                                                        @PathVariable @Positive Long commentId) {
        log.info("Пользователь с идентификатором {}, обновил комментарий {}", authorId, commentId);
        UserCommentOutputDto comment = commentService.userUpdateComment(userUpdateCommentInputDto, authorId, commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Positive Long authorId,
                                              @PathVariable @Positive Long commentId) {
        log.info("Пользователь с идентификатором {}, удалил комментарий {}", authorId, commentId);
        commentService.userDeleteComment(authorId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
