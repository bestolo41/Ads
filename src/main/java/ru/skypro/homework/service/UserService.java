package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.exception.InvalidPasswordException;
import ru.skypro.homework.exception.UserNotAuthorizedException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.dao.UserDAO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final ImageService imageService;
    private final PasswordEncoder encoder;

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
        String encodeNewPassword = encoder.encode(newPasswordDTO.getNewPassword());
        user.setPassword(encodeNewPassword);
        userDAO.updateUser(user);

    }

    public UpdateUserDTO updateUserInformation(UpdateUserDTO updateUserDTO) throws UserNotAuthorizedException {
        User user = getAuthorizedUser();
        user.setFirstname(updateUserDTO.getFirstName());
        user.setLastname(updateUserDTO.getLastName());
        user.setPhone(updateUserDTO.getPhone());

        userDAO.updateUser(user);
        return updateUserDTO;
    }

    public void updateUserImage(MultipartFile image) throws UserNotAuthorizedException, IOException {
        User user = getAuthorizedUser();
        user.setImagePath(imageService.uploadUserImage(image));
        userDAO.updateUser(user);
    }


}
