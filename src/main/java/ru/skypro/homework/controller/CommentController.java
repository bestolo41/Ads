package ru.skypro.homework.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.service.CommentService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads/{id}/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentsDTO> getComments(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(id));
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@PathVariable int id, @RequestBody CreateOrUpdateCommentDTO createComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.addComment(id, createComment));
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> removeComment(@PathVariable int commentId) {
        try {
            commentService.removeComment(commentId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable int commentId, @RequestBody CreateOrUpdateCommentDTO updateCommentDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, updateCommentDTO));
        } catch (UserNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
