package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.NamedEntityGraph;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateCommentDTO {
    private String text;
}
