package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис с бизнес-логикой для работы с изображениями
 */
@Service

public class ImageService {
    @Value("${path.to.user.images}")
    private String userImagePath;

    private final UserService userService;

    @Lazy
    public ImageService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Загружает аватар пользователя в директорию и возвращает путь к нему
     * @param image - изображение
     * @return Пуь к изображению
     * @throws IOException - выбрасывает если произошла ошибка сохранения изображения в директорию
     * @throws UserNotAuthorizedException - выбрасывает если пользоватеь не авторизован
     */
    public String uploadUserImage(MultipartFile image) throws IOException, UserNotAuthorizedException {
        User user = userService.getAuthorizedUser();
        Path imagePath = Path.of(userImagePath + user.getUsername(),
                "user_image.jpg");
        Files.createDirectories(imagePath.getParent());
        Files.deleteIfExists(imagePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(imagePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(os);
        ) {
            bis.transferTo(bos);
        }
        return "/" + imagePath.getParent().toString();
    }

    /**
     * Загружает изображение объявления в директорию и возвращает путь к нему
     * @param image - изображение
     * @return Пуь к изображению
     * @throws IOException - выбрасывает если произошла ошибка сохранения изображения в директорию
     * @throws UserNotAuthorizedException - выбрасывает если пользоватеь не авторизован
     */
    public String uploadAdImage(String adTitle, MultipartFile image) throws IOException, UserNotAuthorizedException {
        User user = userService.getAuthorizedUser();
        Path imagePath = Path.of(userImagePath + user.getUsername() + "/ads/" + adTitle,
                "ad_image.jpg");
        Files.createDirectories(imagePath.getParent());
        Files.deleteIfExists(imagePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(imagePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(os);
        ) {
            bis.transferTo(bos);
        }
        return "/" + imagePath.getParent().toString().replace("\\", "/");
    }

}
