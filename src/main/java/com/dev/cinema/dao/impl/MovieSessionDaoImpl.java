package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        return null;
    }

    @Override
    public List<MovieSession> getByMovieAndDate(Long movieId, LocalDate date) {
        return null;
    }

    @Override
    public List<MovieSession> getAll() {
        return null;
    }
}
