package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.DAO.CommentDAO;
import ru.skypro.homework.service.mapper.CommentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentDAO commentDAO;
    private final UserService userService;
    private final CommentMapper mapper;

    public CommentDTO addComment(int id, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) throws UserNotAuthorizedException {
        User author = userService.getAuthorizedUser();
        Comment comment = new Comment(author, id, createOrUpdateCommentDTO.getText());
        commentDAO.addComment(comment);

        return mapper.commentToCommentDTO(comment);
    }

    public CommentsDTO getComments(int id) {
        List<Comment> comments = commentDAO.getAllComments().stream()
                .filter(comment -> comment.getAdId() == id)
                .collect(Collectors.toList());

        return new CommentsDTO(comments.size(), mapper.commentListToCommentDTOList(comments));
    }

    public void removeComment( int commentId) {
        Comment comment = commentDAO.getCommentById(commentId);
        commentDAO.removeComment(comment);
    }

    public CreateOrUpdateCommentDTO updateComment(int commentId, CreateOrUpdateCommentDTO updateCommentDTO) {
        Comment comment = commentDAO.getCommentById(commentId);
        comment.setText(updateCommentDTO.getText());
        commentDAO.updateComment(comment);
        return updateCommentDTO;
    }
}
