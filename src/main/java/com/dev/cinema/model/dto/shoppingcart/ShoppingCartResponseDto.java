package com.dev.cinema.model.dto.shoppingcart;

import com.dev.cinema.model.dto.ticket.TicketResponseDto;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<TicketResponseDto> tickets;
}
