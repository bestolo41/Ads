package ru.skypro.homework.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.HibernateSessionFactoryUtil;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Service
public class AdDAO {

    public Ad getAdById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Ad.class, id);
    }

    public void addAd(Ad newAd) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(newAd);
            transaction.commit();
        }
    }

    public List<Ad> getAllAds() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
            List<Ad> ads = (List<Ad>) session.createQuery("From Ad").list();
            return ads;
        }
    }

    public void updateAd(Ad ad) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(ad);
            transaction.commit();
        }
    }

    public void removeAdById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(id);
            transaction.commit();
        }
    }
}
