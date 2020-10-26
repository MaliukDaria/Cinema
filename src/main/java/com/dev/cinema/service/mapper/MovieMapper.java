package com.dev.cinema.service.mapper;

import com.dev.cinema.model.Movie;
import com.dev.cinema.model.dto.movie.MovieRequestDto;
import com.dev.cinema.model.dto.movie.MovieResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieResponseDto mapToMovieResponseDto(Movie movie) {
        MovieResponseDto responseDto = new MovieResponseDto();
        responseDto.setMovieId(movie.getId());
        responseDto.setDescription(movie.getDescription());
        responseDto.setTitle(movie.getTitle());
        return responseDto;
    }

    public Movie mapToMovie(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setDescription(movieRequestDto.getDescription());
        movie.setTitle(movieRequestDto.getTitle());
        return movie;
    }
}
