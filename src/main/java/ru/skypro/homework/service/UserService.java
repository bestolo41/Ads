package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.exception.InvalidPasswordException;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.DAO.UserDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    @Value("${path.to.user.images}/")
    static private String pathToUserImages;
    private final UserDAO userDAO;

    public User getAuthorizedUser() throws UserNotAuthorizedException {
        User searchedUser = new User();
        searchedUser.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<User> users = userDAO.getAllUsers();
        if (users.contains(searchedUser)) {
            return users.get(users.indexOf(searchedUser));
        } else {
            throw new UserNotAuthorizedException("Ошибка получения информации авторизованного пользователя");
        }
    }

    public void setPassword(NewPasswordDTO newPasswordDTO) throws UserNotAuthorizedException, InvalidPasswordException {
        User user = getAuthorizedUser();
        if (!user.getPassword().equals(newPasswordDTO.getCurrentPassword())) {
            throw new InvalidPasswordException("Неверный текущий пароль");
        } else {
            user.setPassword(newPasswordDTO.getNewPassword());
            userDAO.updateUser(user);
        }
    }

    public UpdateUserDTO updateUserInformation(UpdateUserDTO updateUserDTO) throws UserNotAuthorizedException {
        User user = getAuthorizedUser();
        user.setFirstname(updateUserDTO.getFirstName());
        user.setLastname(updateUserDTO.getLastName());
        user.setPhone(updateUserDTO.getPhone());

        userDAO.updateUser(user);
        return updateUserDTO;
    }

    public void updateUserImage(MultipartFile file) throws UserNotAuthorizedException {

        User user = getAuthorizedUser();
        String imageAddress = "/users/photo/" + user.getId();
        File tempFile = new File(
                Path.of(pathToUserImages).toAbsolutePath().toFile(),
                user.getId() + "_user_image.jpg");
        writeFile(tempFile, file);
        user.setImagePath(imageAddress);
        userDAO.updateUser(user);
    }

    private void writeFile(File tempFile, MultipartFile file) {
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!");
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
    }
}
