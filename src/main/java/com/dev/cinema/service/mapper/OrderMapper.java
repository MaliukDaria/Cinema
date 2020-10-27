package com.dev.cinema.service.mapper;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.dto.order.OrderResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponseDto mapToOrderResponseDto(Order order) {
        List<Long> ticketIds = order.getTickets().stream()
                .map(Ticket::getId)
                .collect(Collectors.toList());
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setTicketIds(ticketIds);
        responseDto.setId(order.getId());
        responseDto.setOrderDate(order.getOrderDate());
        return responseDto;
    }
}
