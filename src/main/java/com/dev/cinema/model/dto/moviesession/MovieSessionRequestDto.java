package com.dev.cinema.model.dto.moviesession;

import lombok.Data;

@Data
public class MovieSessionRequestDto {
    private String showTime;
    private Long movieId;
    private Long cinemaHallId;
}
