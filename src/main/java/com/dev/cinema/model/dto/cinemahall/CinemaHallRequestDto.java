package com.dev.cinema.model.dto.cinemahall;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CinemaHallRequestDto {
    @Size(min = 10)
    private int capacity;
    @NotNull
    private String description;
}
