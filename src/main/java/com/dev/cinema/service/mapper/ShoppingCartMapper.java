package com.dev.cinema.service.mapper;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.dto.shoppingcart.ShoppingCartResponseDto;
import com.dev.cinema.model.dto.ticket.TicketResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {
    private final TicketMapper ticketMapper;

    public ShoppingCartMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public ShoppingCartResponseDto mapToShoppingCartResponseDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto();
        responseDto.setId(shoppingCart.getId());
        responseDto.setUserId(shoppingCart.getUser().getId());
        List<TicketResponseDto> ticketsResponseDto = shoppingCart.getTickets().stream()
                .map(ticketMapper::mapToTicketResponseDto)
                .collect(Collectors.toList());
        responseDto.setTickets(ticketsResponseDto);
        return responseDto;
    }
}
