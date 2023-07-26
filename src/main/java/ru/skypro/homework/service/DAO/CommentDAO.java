package ru.skypro.homework.service.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.HibernateSessionFactoryUtil;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Service
public class CommentDAO {

    public Comment getCommentById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Comment.class, id);
    }

    public void addComment(Comment newComment) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(newComment);
            transaction.commit();
        }
    }

    public List<Comment> getAllComments() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            List<Comment> comments = (List<Comment>) session.createQuery("From Comment").list();
            return comments;
        }
    }

    public void updateComment(Comment comment) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(comment);
            transaction.commit();
        }
    }

    public void removeComment(Comment comment) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(comment);
            transaction.commit();
        }
    }

}
