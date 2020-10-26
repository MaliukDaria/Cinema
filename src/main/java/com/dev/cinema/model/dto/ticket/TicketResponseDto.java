package com.dev.cinema.model.dto.ticket;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TicketResponseDto {
    private Long id;
    private Long movieSessionId;
    private String movieTitle;
    private LocalDateTime movieSessionShowTime;
    private String cinemaHallDescription;
}
