package com.dev.cinema;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.lib.Injector;
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
        availableSessions.forEach(System.out::println);

        User alise = new User("alise@mail.com", "1111");
        User bob = new User("bob@mail.com", "bob");
        User badBob = new User("badBob@mail.com", "1234");
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            authenticationService.login(badBob.getEmail(), badBob.getPassword());
        } catch (AuthenticationException e) {
            System.out.println("Expected AuthenticationException:\n " + e + "\n");
        }
        alise = authenticationService.register(alise.getEmail(), alise.getPassword());
        bob = authenticationService.register(bob.getEmail(), bob.getPassword());
        try {
            System.out.println("Expected alise:\n "
                    + authenticationService.login(alise.getEmail(), alise.getPassword()) + "\n");
        } catch (AuthenticationException e) {
            System.out.println("Can't login " + e);
        }
        UserService userService = (UserService) injector.getInstance(UserService.class);
        userService.add(badBob);
        System.out.println("Expected " + badBob + "\n"
                + userService.findByEmail(badBob.getEmail()).orElseThrow() + "\n");
        try {
            authenticationService.register(badBob.getEmail(), "2222");
        } catch (Exception e) {
            System.out.println("Expected exception: \n" + e + "\n");
        }
        try {
            authenticationService.login(alise.getEmail(), "invalid password");
        } catch (AuthenticationException e) {
            System.out.println("Expected \"Incorrect login or password\" :" + e);
        }

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(todayPulpFicMovieSession, alise);
        shoppingCartService.addSession(todayLordMovieSession, alise);
        shoppingCartService.addSession(tomorrowMovieSession, bob);
        ShoppingCart aliseShoppingCart = shoppingCartService.getByUser(alise);
        System.out.println("Expected 2: \n" + aliseShoppingCart.getTickets().size());
        shoppingCartService.clear(aliseShoppingCart);
        System.out.println("Expected 0: \n" + aliseShoppingCart.getTickets().size());

        shoppingCartService.addSession(todayPulpFicMovieSession, alise);
        shoppingCartService.addSession(todayLordMovieSession, alise);
        shoppingCartService.addSession(tomorrowMovieSession, alise);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        aliseShoppingCart = shoppingCartService.getByUser(alise);
        List<Ticket> aliseTickets = aliseShoppingCart.getTickets();
        System.out.println("----------");
        orderService.completeOrder(aliseTickets, alise);
        shoppingCartService.addSession(tomorrowMovieSession, alise);
        aliseShoppingCart = shoppingCartService.getByUser(alise);
        orderService.completeOrder(aliseShoppingCart.getTickets(), alise);
        List<Order> aliseOrderHistory = orderService.getOrderHistory(alise);
        System.out.println();
    }
}
