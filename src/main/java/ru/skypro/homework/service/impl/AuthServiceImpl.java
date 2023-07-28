package ru.skypro.homework.service.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.JdbcUserDetailService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.dao.UserDAO;

@Service
public class AuthServiceImpl implements AuthService {

    private final JdbcUserDetailService userService;
    private final PasswordEncoder encoder;
    private final UserDAO userDAO;
    private final UserMapper mapper;

    public AuthServiceImpl(JdbcUserDetailService userService,
                           PasswordEncoder passwordEncoder, UserDAO userDAO, UserMapper mapper) {
        this.userService = userService;
        this.encoder = passwordEncoder;
        this.userDAO = userDAO;
        this.mapper = mapper;
    }

    @Override
    public boolean login(String userName, String password) {
        if (userDAO.getUserByUsername(userName).isEmpty()) {
            return false;
        }
        UserDetails userDetails = userService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDTO register) {
        if (userDAO.getUserByUsername(register.getUsername()).isPresent()) {
            return false;
        }
        User user = mapper.registerDTOtoUser(register);
        userDAO.addUser(mapper.registerDTOtoUser(register));
        return true;
    }

}
