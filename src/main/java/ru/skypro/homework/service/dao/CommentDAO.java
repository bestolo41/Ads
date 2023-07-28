package ru.skypro.homework.service.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.HibernateSessionFactoryUtil;
import ru.skypro.homework.model.Comment;

import java.util.List;

/**
 * Сервис для работы с сущностями Comment в БД
 */
@Service
public class CommentDAO {

    /**
     * Получает коммент по его идентификатору
     * @param id - идентификатор коммента
     * @return Comment - коммент-сущность
     */
    public Comment getCommentById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Comment.class, id);
    }

    /**
     * Добавляет новый коммент в БД
     * @param newComment - новый коммент-сущность
     */
    public void addComment(Comment newComment) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(newComment);
            transaction.commit();
        }
    }

    /**
     * Возвращает коллекцию со всеми комментами из БД
     * @return List<Comment></Comment>
     */
    public List<Comment> getAllComments() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            List<Comment> comments = (List<Comment>) session.createQuery("From Comment").list();
            return comments;
        }
    }

    /**
     * Обновляет коммент в БД
     * @param comment - обновляемый коммент-сущность
     */
    public void updateComment(Comment comment) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(comment);
            transaction.commit();
        }
    }

    /**
     * Удаляет коммент из БД
     * @param comment - удаляемый коммент-сущность
     */
    public void removeComment(Comment comment) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(comment);
            transaction.commit();
        }
    }

}
