package ru.skypro.homework.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.User;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder encoder;

    public User registerDTOtoUser(RegisterDTO dto) {
        User user = new User();
        user.setFirstname(dto.getFirstName());
        user.setLastname(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());

        return user;
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getPhone(),
                user.getImagePath(),
                user.getRole().getName());
    }
}
