package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl extends GenericDaoImpl<ShoppingCart> implements ShoppingCartDao {
    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<ShoppingCart> getShoppingCartQuery = session.createQuery(
                    "FROM ShoppingCart sc "
                            + "JOIN FETCH sc.user u "
                            + "JOIN FETCH u.roles "
                            + "LEFT JOIN FETCH sc.tickets "
                            + "WHERE sc.user.id = :userId",
                    ShoppingCart.class)
                    .setParameter("userId", user.getId());
            return getShoppingCartQuery.getSingleResult();
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(shoppingCart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Cant update shopping cart with user id: "
                            + shoppingCart.getUser().getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
