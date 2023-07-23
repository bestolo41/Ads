package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import ru.skypro.homework.dto.AuthorizedUserDTO;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.UserForUpdateDTO;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public PasswordDTO setPassword(@RequestBody PasswordDTO password) {
        return password;
    }

    @GetMapping("/me")
    public ResponseEntity<AuthorizedUserDTO> getAuthorizedUserInformation() {
        AuthorizedUserDTO user = new AuthorizedUserDTO(0, "ibragimovbi@mail.ru", "Bulat", "Ibragimov", "89874092753",
                "C:\\Users\\BULAT\\Desktop\\koshka-kot-polosatyj-glaza-zelenye-sidit-lavka-skamya.jpg");
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/me")
    public UserForUpdateDTO updateUserInformation(@RequestBody UserForUpdateDTO userForUpdate) {
        return userForUpdate;
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateUserImage(@RequestBody MultipartFile image) {
        return ResponseEntity.ok().build();
    }
}
