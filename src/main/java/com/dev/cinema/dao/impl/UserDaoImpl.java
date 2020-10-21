package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.model.User;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> getAllUsersQuery = session.createQuery(
                    "FROM User", User.class);
            return getAllUsersQuery.getResultList();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> findByEmailQuery = session.createQuery(
                    "FROM User WHERE email = :userEmail", User.class)
                    .setParameter("userEmail", email);
            return findByEmailQuery.uniqueResultOptional();
        }
    }
}
