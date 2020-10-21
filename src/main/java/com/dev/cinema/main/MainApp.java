package com.dev.cinema.main;

import com.dev.cinema.config.AppConfig;
import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    private static final Logger logger = Logger.getLogger(MainApp.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        MovieService movieService = context.getBean(MovieService.class);
        movieService.getAll().forEach(logger::info);
        Movie pulpFiction = new Movie("Pulp Fiction", "Best movie");
        Movie theLordOfTheRings = new Movie("The Lord of the Rings", "Lord");
        movieService.add(pulpFiction);
        movieService.add(theLordOfTheRings);
        movieService.getAll().forEach(logger::info);

        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);
        CinemaHall redCinemaHall = new CinemaHall(13, "Red cinema hall");
        CinemaHall blueCinemaHall = new CinemaHall(100, "Blue cinema hall");
        cinemaHallService.add(redCinemaHall);
        cinemaHallService.add(blueCinemaHall);

        MovieSession todayPulpFicMovieSession = new MovieSession(
                LocalDateTime.now().plusHours(3), pulpFiction, redCinemaHall);
        MovieSession secondTodayPulpFicMovieSession = new MovieSession(
                LocalDateTime.now().plusHours(2), pulpFiction, blueCinemaHall);
        MovieSession todayLordMovieSession = new MovieSession(
                LocalDateTime.now().plusHours(1), theLordOfTheRings, blueCinemaHall);
        MovieSessionService movieSessionService = context.getBean(MovieSessionService.class);
        movieSessionService.add(todayPulpFicMovieSession);
        movieSessionService.add(secondTodayPulpFicMovieSession);
        movieSessionService.add(todayLordMovieSession);
        MovieSession tomorrowMovieSession = new MovieSession(
                LocalDateTime.now().plusDays(1), pulpFiction, blueCinemaHall);
        movieSessionService.add(tomorrowMovieSession);
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(pulpFiction.getId(), LocalDate.now());
        availableSessions.forEach(logger::info);

        User alise = new User("alise@mail.com", "1111");
        User bob = new User("bob@mail.com", "bob");
        User badBob = new User("badBob@mail.com", "1234");
        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
        try {
            authenticationService.login(badBob.getEmail(), badBob.getPassword());
        } catch (AuthenticationException e) {
            logger.info("Expected AuthenticationException:\n " + e);
        }
        alise = authenticationService.register(alise.getEmail(), alise.getPassword());
        bob = authenticationService.register(bob.getEmail(), bob.getPassword());
        try {
            logger.info("Expected alise:\n "
                    + authenticationService.login(alise.getEmail(), "1111") + "\n");
        } catch (AuthenticationException e) {
            logger.warn("Login failed: " + e);
        }
        UserService userService = context.getBean(UserService.class);
        userService.add(badBob);
        logger.info("Expected " + badBob + "\n"
                + userService.findByEmail(badBob.getEmail()).orElseThrow() + "\n");
        try {
            authenticationService.register(badBob.getEmail(), "2222");
        } catch (Exception e) {
            logger.info("Expected exception: \n" + e + "\n");
        }
        try {
            authenticationService.login(alise.getEmail(), "invalid password");
        } catch (AuthenticationException e) {
            logger.info("Expected \"Incorrect login or password\" :" + e);
        }

        ShoppingCartService shoppingCartService = context.getBean(ShoppingCartService.class);
        shoppingCartService.addSession(todayPulpFicMovieSession, alise);
        shoppingCartService.addSession(todayLordMovieSession, alise);
        shoppingCartService.addSession(tomorrowMovieSession, bob);
        ShoppingCart aliseShoppingCart = shoppingCartService.getByUser(alise);
        logger.info("Expected 2: \n" + aliseShoppingCart.getTickets().size());
        shoppingCartService.clear(aliseShoppingCart);
        logger.info("Expected 0: \n" + aliseShoppingCart.getTickets().size());

        shoppingCartService.addSession(todayPulpFicMovieSession, alise);
        shoppingCartService.addSession(todayLordMovieSession, alise);
        shoppingCartService.addSession(tomorrowMovieSession, alise);
        aliseShoppingCart = shoppingCartService.getByUser(alise);
        List<Ticket> aliseTickets = aliseShoppingCart.getTickets();
        OrderService orderService = context.getBean(OrderService.class);
        orderService.completeOrder(aliseTickets, alise);
        shoppingCartService.addSession(tomorrowMovieSession, alise);
        aliseShoppingCart = shoppingCartService.getByUser(alise);
        orderService.completeOrder(aliseShoppingCart.getTickets(), alise);
        List<Order> aliseOrderHistory = orderService.getOrderHistory(alise);
        aliseOrderHistory.forEach(logger::info);
    }
}
