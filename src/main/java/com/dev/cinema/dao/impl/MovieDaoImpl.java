package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieDao;
import com.dev.cinema.model.Movie;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDaoImpl extends GenericDaoImpl<Movie> implements MovieDao {
    public MovieDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Movie> getAllMoviesQuery = session.createQuery("FROM Movie", Movie.class);
            return getAllMoviesQuery.getResultList();
        }
    }
}
