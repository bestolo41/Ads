package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс для преобразования сущности Comment в CommentDTO
 */
@Service
public class CommentMapper {

    /**
     * Преобразует Comment -> CommentDTO
     * @param comment
     * @return
     */
    public CommentDTO commentToCommentDTO(Comment comment) {
        return new CommentDTO(comment.getAuthor().getId(),
                comment.getAuthor().getImagePath(),
                comment.getAuthor().getFirstname(),
                comment.getCreateAt(),
                comment.getPk(),
                comment.getText());
    }

    /**
     * Преобразует коллекцию с Comment в коллекцию с CommentDTO
     * @param commentList
     * @return
     */
    public List<CommentDTO> commentListToCommentDTOList(List<Comment> commentList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentDTOList.add(commentToCommentDTO(comment));
        }
        return commentDTOList;
    }


}
