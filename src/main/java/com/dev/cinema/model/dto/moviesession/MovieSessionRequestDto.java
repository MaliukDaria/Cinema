package com.dev.cinema.model.dto.moviesession;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
    @NotNull
    private String showTime;
    @NotNull
    private Long movieId;
    @NotNull
    private Long cinemaHallId;
}
