package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.comment.dto.UserCommentOutputDto;
import ru.practicum.comment.service.PublicCommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class PublicCommentController {
    private final PublicCommentService service;

    @GetMapping("/{commentId}")
    public ResponseEntity<UserCommentOutputDto> getCommentById(@PathVariable Long commentId) {
        log.info("Получен запрос на получение комментария по идентификатору {}", commentId);
        UserCommentOutputDto comment = service.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<UserCommentOutputDto>> getAllEventComments(@PathVariable Long eventId,
                                                                          @RequestParam(defaultValue = "0") int from,
                                                                          @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос на получение всех комментарий события с идентификатором {}", eventId);
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        List<UserCommentOutputDto> comments = service.getAllEventComments(eventId, pageRequest);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
