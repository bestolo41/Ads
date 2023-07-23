package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads/{id}/comments")
public class CommentController {

    @GetMapping
    public ResponseEntity<CommentsDTO> getComments(@PathVariable int id) {
        return ResponseEntity.ok().body(new CommentsDTO());
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@PathVariable int id, @RequestBody CreateOrUpdateCommentDTO createComment) {
        return ResponseEntity.ok().body(new CommentDTO());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> removeComment(@PathVariable int adId, @PathVariable int commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable int adId, @PathVariable int commentId) {
        return ResponseEntity.ok().build();
    }
}
