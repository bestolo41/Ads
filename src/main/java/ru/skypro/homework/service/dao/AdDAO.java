package ru.skypro.homework.service.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.HibernateSessionFactoryUtil;
import ru.skypro.homework.model.Ad;

import java.util.List;

/**
 * Сервис для работы с сущностями Ad в БД
 */
@Service
public class AdDAO {
    /**
     * Получает объявление из БД по идентификатору
     * @param id - идентификатор объявления
     * @return Ad -сущность объявления
     */

    public Ad getAdById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Ad.class, id);
    }

    /**
     * Добавляет новое объявление в БД
     * @param newAd - Ad сущность нового объявления
     */
    public void addAd(Ad newAd) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(newAd);
            transaction.commit();
        }
    }

    /**
     * Возвращает коллекцию со всеми объявлениями из БД
     * @return List<Ad>
     */
    public List<Ad> getAllAds() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            List<Ad> ads = (List<Ad>) session.createQuery("From Ad").list();
            return ads;
        }
    }

    /**
     * Обновляет объявление в БД
     * @param ad - сущность обновленного объявления
     */
    public void updateAd(Ad ad) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(ad);
            transaction.commit();
        }
    }

    /**
     * Удаляет объявление из БД
     * @param ad - сущность удаляемого объявления
     */
    public void removeAd(Ad ad) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(ad);
            transaction.commit();
        }
    }
}
