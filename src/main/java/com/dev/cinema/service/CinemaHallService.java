package com.dev.cinema.service;

import com.dev.cinema.model.CinemaHall;
import java.util.List;

public interface CinemaHallService extends GenericService<CinemaHall> {
    List<CinemaHall> getAll();
}
