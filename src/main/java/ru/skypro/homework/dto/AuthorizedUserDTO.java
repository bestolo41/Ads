package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedUserDTO {
    private int id;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String image;
}
