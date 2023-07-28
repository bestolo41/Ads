package ru.skypro.homework.service.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.HibernateSessionFactoryUtil;
import ru.skypro.homework.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с сущностями User в БД
 */
@Service
public class UserDAO {
    /**
     * Добавляет нового пользователя в БД
     * @param newUser - новый пользователь-сущность
     */
    public void addUser(User newUser) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(newUser);
            transaction.commit();
        }
    }

    /**
     * Получает пользователя из БД по логину
     * @param username - логин пользователя
     * @return Optional<User> - optional с пользователем-сущностью
     */
    public Optional<User> getUserByUsername(String username) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    /**
     * Возвращает коллекцию со всеми пользователями из БД
     * @return List<User>
     */
    public List<User> getAllUsers() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            List<User> users = (List<User>) session.createQuery("From User").list();
            return users;
        }
    }

    /**
     * Обновляет пользователя в БД
     * @param user - обновляемый пользователь-сущность
     */
    public void updateUser(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }
}
