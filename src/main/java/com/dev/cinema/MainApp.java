package com.dev.cinema;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainApp {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) throws AuthenticationException {
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
        availableSessions.forEach(System.out::println);

        User alise = new User("alise@mail.com", "1111");
        User bob = new User("bob@mail.com", "1234");
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.login(bob.getEmail(), bob.getPassword());
        } catch (AuthenticationException e) {
            System.out.println("Expected AuthenticationException:\n " + e + "\n");
        }
        authenticationService.register(alise.getEmail(), alise.getPassword());
        System.out.println("Expected alise:\n "
                + authenticationService.login(alise.getEmail(), alise.getPassword()) + "\n");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        userService.add(bob);
        System.out.println("Expected " + bob + "\n"
                + userService.findByEmail(bob.getEmail()).orElseThrow() + "\n");
        try {
            authenticationService.register(bob.getEmail(), "2222");
        } catch (Exception e) {
            System.out.println("Expected exception: \n" + e + "\n");
        }
        try {
            authenticationService.login(alise.getEmail(), "invalid password");
        } catch (AuthenticationException e) {
            System.out.println("Expected \"Incorrect login or password\" :" + e);
        }
    }
}
