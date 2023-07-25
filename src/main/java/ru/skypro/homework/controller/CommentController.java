package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads/{id}/comments")
public class CommentController {

    @GetMapping
    public ResponseEntity<CommentsDTO> getComments(@PathVariable int id) {
        List<CommentDTO> comments = new ArrayList<>();
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(comments.size());
        commentsDTO.setResults(comments);
        return ResponseEntity.status(HttpStatus.OK).body(commentsDTO);
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
