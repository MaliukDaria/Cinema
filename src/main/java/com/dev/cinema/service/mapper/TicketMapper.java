package com.dev.cinema.service.mapper;

import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.dto.ticket.TicketResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketResponseDto mapToTicketResponseDto(Ticket ticket) {
        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setId(ticket.getId());
        responseDto.setMovieTitle(ticket.getMovieSession().getMovie().getTitle());
        responseDto.setCinemaHallDescription(
                ticket.getMovieSession().getCinemaHall().getDescription());
        responseDto.setMovieSessionId(ticket.getMovieSession().getId());
        responseDto.setMovieSessionShowTime(ticket.getMovieSession().getShowTime());
        return responseDto;
    }
}
