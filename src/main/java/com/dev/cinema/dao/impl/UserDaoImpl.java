package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.User;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> getAllUsersQuery = session.createQuery(
                    "FROM User", User.class);
            return getAllUsersQuery.getResultList();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> findByEmailQuery = session.createQuery(
                    "FROM User WHERE email = :userEmail", User.class)
                    .setParameter("userEmail", email);
            return findByEmailQuery.uniqueResultOptional();
        }
    }
}
