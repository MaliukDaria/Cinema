package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.TicketDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class TicketDaoImpl implements TicketDao {
    @Override
    public Ticket add(Ticket ticket) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(ticket);
            transaction.commit();
            return ticket;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Cant add ticket with movie session " + ticket.getMovieSession()
                            + " to the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Ticket> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Ticket> getAllTicketsQuery = session.createQuery(
                    "FROM Ticket", Ticket.class);
            return getAllTicketsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get all tickets from the database", e);
        }
    }
}
