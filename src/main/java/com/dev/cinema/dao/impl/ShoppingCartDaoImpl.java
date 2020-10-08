package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Cant add shopping cart with user id: " + shoppingCart.getUser().getId()
                            + " to the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ShoppingCart> getShoppingCartQuery = session.createQuery(
                    "FROM ShoppingCart sc "
                            + "JOIN FETCH sc.user "
                            + "LEFT JOIN FETCH sc.tickets AS t "
                            + "WHERE sc.user.id = :userId",
                    ShoppingCart.class)
                    .setParameter("userId", user.getId());
            return getShoppingCartQuery.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get shopping cart with user id: " + user.getId(), e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
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

    @Override
    public List<ShoppingCart> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ShoppingCart> getAllShoppingCartsQuery = session.createQuery(
                    "FROM ShoppingCart", ShoppingCart.class);
            return getAllShoppingCartsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get all shopping carts from the database", e);
        }
    }
}
