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
        Movie pulpFiction = new Movie();
        pulpFiction.setTitle("Pulp Fiction");
        pulpFiction.setDescription("Best movie");
        Movie theLordOfTheRings = new Movie();
        theLordOfTheRings.setTitle("The Lord of the Rings");
        theLordOfTheRings.setDescription("Lord");
        movieService.add(pulpFiction);
        movieService.add(theLordOfTheRings);
        movieService.get(1L);
        movieService.getAll().forEach(logger::info);

        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(13);
        redCinemaHall.setDescription("Red cinema hall");
        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(100);
        blueCinemaHall.setDescription("Blue cinema hall");
        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);
        cinemaHallService.add(redCinemaHall);
        cinemaHallService.add(blueCinemaHall);

        MovieSession todayPulpFicMovieSession = new MovieSession();
        todayPulpFicMovieSession.setMovie(pulpFiction);
        todayPulpFicMovieSession.setCinemaHall(redCinemaHall);
        todayPulpFicMovieSession.setShowTime(LocalDateTime.now().plusHours(3));
        MovieSession secondTodayPulpFicMovieSession = new MovieSession();
        secondTodayPulpFicMovieSession.setMovie(pulpFiction);
        secondTodayPulpFicMovieSession.setCinemaHall(blueCinemaHall);
        secondTodayPulpFicMovieSession.setShowTime(LocalDateTime.now().plusHours(2));
        MovieSession todayLordMovieSession = new MovieSession();
        todayLordMovieSession.setMovie(theLordOfTheRings);
        todayLordMovieSession.setCinemaHall(blueCinemaHall);
        todayLordMovieSession.setShowTime(LocalDateTime.now().plusHours(1));
        MovieSessionService movieSessionService = context.getBean(MovieSessionService.class);
        movieSessionService.add(todayPulpFicMovieSession);
        movieSessionService.add(secondTodayPulpFicMovieSession);
        movieSessionService.add(todayLordMovieSession);
        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setMovie(pulpFiction);
        tomorrowMovieSession.setCinemaHall(blueCinemaHall);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(tomorrowMovieSession);
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(pulpFiction.getId(), LocalDate.now());
        availableSessions.forEach(logger::info);

        User alise = new User();
        alise.setEmail("alise@mail.com");
        alise.setPassword("1111");
        User bob = new User();
        bob.setEmail("bob@mail.com");
        bob.setPassword("bob");
        User badBob = new User();
        badBob.setEmail("badBob@mail.com");
        badBob.setPassword("1234");
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
