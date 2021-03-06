package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl extends GenericDaoImpl<MovieSession> implements MovieSessionDao {
    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<MovieSession> getByMovieAndDate(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> movieSessionQuery =
                    session.createQuery("FROM MovieSession ms "
                                    + "WHERE ms.movie.id = :id "
                                    + "AND ms.showTime BETWEEN :start AND :end",
                            MovieSession.class)
                            .setParameter("id", movieId)
                            .setParameter("start", date.atStartOfDay())
                            .setParameter("end", date.atTime(LocalTime.MAX));
            return movieSessionQuery.getResultList();
        }
    }
}
