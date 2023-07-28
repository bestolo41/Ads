package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.dao.CommentDAO;
import ru.skypro.homework.service.mapper.CommentMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для бизнес-логики для работы с сущностями Comment
 */
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

    /**
     * Возвращает CommentDTO, который содержит коллекцию комментариев(CommentDTO) с идентификатором объявления id
     * @param id - идентификатор объявления
     * @return
     */
    public CommentsDTO getComments(int id) {
        List<Comment> comments = commentDAO.getAllComments().stream()
                .filter(comment -> comment.getAdId() == id)
                .collect(Collectors.toList());

        return new CommentsDTO(comments.size(), mapper.commentListToCommentDTOList(comments));
    }

    /**
     * Удаляет комментарий из БД
     * @param commentId - идентификатор комментария
     * @throws UserNotAuthorizedException - выбрасывает если пользователь не авторизован
     * @throws NoAccessException - выбрасывает если пользователь не является автором комментария
     */
    public void removeComment(int commentId) throws UserNotAuthorizedException, NoAccessException {
        User user = userService.getAuthorizedUser();
        Comment comment = commentDAO.getCommentById(commentId);

        if (comment.getAuthor().getId() == user.getId() || user.getRole().equals(Role.ADMIN)) {
            commentDAO.removeComment(comment);
        } else {
            throw new NoAccessException("No access");
        }
    }

    /**
     * Обновляет комментарий в БД и возвращает новые данные для отображения
     * @param commentId - идентификатор комментария
     * @param updateCommentDTO - DTO с обновленным комментарием
     * @return CreateOrUpdateCommentDTO
     * @throws UserNotAuthorizedException - выбрасывает если пользователь не авторизован
     * @throws NoAccessException - выбрасывает если пользователь не является автором комментария
     */
    public CreateOrUpdateCommentDTO updateComment(int commentId, CreateOrUpdateCommentDTO updateCommentDTO) throws UserNotAuthorizedException, NoAccessException {
        User user = userService.getAuthorizedUser();
        Comment comment = commentDAO.getCommentById(commentId);

        if (comment.getAuthor().getId() == user.getId() || user.getRole().equals(Role.ADMIN)) {
            comment.setText(updateCommentDTO.getText());
            commentDAO.updateComment(comment);
            return updateCommentDTO;
        } else {
            throw new NoAccessException("No access");
        }
    }
}
