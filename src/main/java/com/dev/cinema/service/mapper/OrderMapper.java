package com.dev.cinema.service.mapper;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.dto.order.OrderResponseDto;
import com.dev.cinema.model.dto.ticket.TicketResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final TicketMapper ticketMapper;

    public OrderMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public OrderResponseDto mapToOrderResponseDto(Order order) {
        List<TicketResponseDto> ticketsResponseDto = order.getTickets().stream()
                .map(ticketMapper::mapToTicketResponseDto)
                .collect(Collectors.toList());
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setTickets(ticketsResponseDto);
        responseDto.setId(order.getId());
        responseDto.setOrderDate(order.getOrderDate());
        return responseDto;
    }
}
