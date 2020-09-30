package com.dev.cinema;

import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;

public class MainApp {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.getAll().forEach(System.out::println);
        Movie movie = new Movie();
        movie.setTitle("Pulp Fiction");
        movie.setDescription("Best movie");
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);
    }
}
