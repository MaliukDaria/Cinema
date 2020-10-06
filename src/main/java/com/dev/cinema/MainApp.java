package com.dev.cinema;

import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainApp {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.getAll().forEach(System.out::println);
        Movie pulpFiction = new Movie("Pulp Fiction", "Best movie");
        Movie theLordOfTheRings = new Movie("The Lord of the Rings", "Lord");
        movieService.add(pulpFiction);
        movieService.add(theLordOfTheRings);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall redCinemaHall = new CinemaHall(13, "Red cinema hall");
        CinemaHall blueCinemaHall = new CinemaHall(100, "Blue cinema hall");
        cinemaHallService.add(redCinemaHall);
        cinemaHallService.add(blueCinemaHall);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession todayPulpFicMovieSession = new MovieSession(
                LocalDateTime.now().plusHours(3), pulpFiction, redCinemaHall);
        MovieSession secondTodayPulpFicMovieSession = new MovieSession(
                LocalDateTime.now().plusHours(2), pulpFiction, blueCinemaHall);
        MovieSession todayLordMovieSession = new MovieSession(
                LocalDateTime.now().plusHours(1), theLordOfTheRings, blueCinemaHall);
        movieSessionService.add(todayPulpFicMovieSession);
        movieSessionService.add(secondTodayPulpFicMovieSession);
        movieSessionService.add(todayLordMovieSession);
        MovieSession tomorrowMovieSession = new MovieSession(
                LocalDateTime.now().plusDays(1), pulpFiction, blueCinemaHall);
        movieSessionService.add(tomorrowMovieSession);

        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(pulpFiction.getId(), LocalDate.now());
        System.out.println(availableSessions);
    }
}
