package com.dev.cinema.model.dto.ticket;

import lombok.Data;

@Data
public class TicketRequestDto {
    private Long movieSessionId;
    private Long userId;
}
