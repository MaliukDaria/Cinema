package com.dev.cinema.dao;

import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao extends GenericDao<MovieSession> {
    List<MovieSession> getByMovieAndDate(Long movieId, LocalDate date);
}
