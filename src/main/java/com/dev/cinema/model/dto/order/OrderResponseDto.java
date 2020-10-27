package com.dev.cinema.model.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private LocalDateTime orderDate;
    private List<Long> ticketIds;
}
