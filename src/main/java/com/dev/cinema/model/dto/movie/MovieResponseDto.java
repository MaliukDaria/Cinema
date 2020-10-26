package com.dev.cinema.model.dto.movie;

import lombok.Data;

@Data
public class MovieResponseDto {
    private Long movieId;
    private String title;
    private String description;
}
