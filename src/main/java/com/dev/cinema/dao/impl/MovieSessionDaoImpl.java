package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Cant add movie session with movie" + movieSession.getMovie()
                            + " to the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<MovieSession> getByMovieAndDate(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery =
                    session.createQuery("FROM MovieSession ms "
                                    + "JOIN FETCH ms.cinemaHall "
                                    + "JOIN FETCH ms.movie "
                                    + "WHERE ms.movie.id = :id "
                                    + "AND ms.showTime BETWEEN :start AND :end",
                            MovieSession.class)
                    .setParameter("id", movieId)
                    .setParameter("start", date.atStartOfDay())
                    .setParameter("end", date.atTime(LocalTime.MAX));
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get movie session by movie and date", e);
        }
    }
}
