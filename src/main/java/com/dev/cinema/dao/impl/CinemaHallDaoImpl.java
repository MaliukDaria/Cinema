package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.CinemaHallDao;
import com.dev.cinema.model.CinemaHall;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaHallDaoImpl extends GenericDaoImpl<CinemaHall> implements CinemaHallDao {
    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<CinemaHall> getAllCinemaHalls = session.createQuery(
                    "FROM CinemaHall", CinemaHall.class);
            return getAllCinemaHalls.getResultList();
        }
    }
}
