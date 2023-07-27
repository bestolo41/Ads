package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentMapper {
    public CommentDTO commentToCommentDTO(Comment comment) {
        return new CommentDTO(comment.getAuthor().getId(),
                comment.getAuthor().getImagePath(),
                comment.getAuthor().getFirstname(),
                comment.getCreateAt(),
                comment.getPk(),
                comment.getText());
    }

    public List<CommentDTO> commentListToCommentDTOList(List<Comment> commentList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentDTOList.add(commentToCommentDTO(comment));
        }
        return commentDTOList;
    }


}
