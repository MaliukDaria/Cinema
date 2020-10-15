package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.GenericDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GenericDaoImpl<T> implements GenericDao<T> {
    private static final Logger logger = Logger.getLogger(GenericDaoImpl.class);

    @Override
    public T add(T item) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            logger.info("Entity " + item + "added into the database");
            return item;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Cant add entity " + item + " to the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
