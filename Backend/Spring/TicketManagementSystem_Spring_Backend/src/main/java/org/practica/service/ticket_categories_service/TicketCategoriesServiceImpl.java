package org.practica.service.ticket_categories_service;

import org.practica.model.TicketCategory;
import org.practica.model.dto.TicketCategoryDto;
import org.practica.repository.TicketCategoriesRepository;
import org.practica.repository.mocks.MockTicketCategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketCategoriesServiceImpl implements TicketCategoriesService{
    private TicketCategoriesRepository ticketCategoriesRepository;
//    private MockTicketCategoriesRepository ticketCategoriesRepository;
    private final Logger logger;

    public TicketCategoriesServiceImpl(
            TicketCategoriesRepository ticketCategoriesRepository
//        MockTicketCategoriesRepository ticketCategoriesRepository
    ){
        this.ticketCategoriesRepository = ticketCategoriesRepository;
        this.logger = LoggerFactory.getLogger(ticketCategoriesRepository.getClass());
    }

    @Override
    public TicketCategory getTicketCategoryById(UUID ticketCategoryId) {
        logger.info("TicketCategoriesRepository - findById(ticketCategoryId)");
        return ticketCategoriesRepository.findById(ticketCategoryId).orElse(null);
    }

    @Override
    public Iterable<TicketCategory> getTicketCategoriesByEventId(UUID eventId) {
        logger.info("TicketCategoriesRepository - getTicketCategoriesByEventId(eventId)");
        return ticketCategoriesRepository.findAllByEventId(eventId);
    }

    @Override
    public TicketCategoryDto getTicketCategoryIdByEventAndType(UUID eventId, String description) {
        logger.info("TicketCategoriesRepository - getTicketCategoryIdByEventAndType(eventId, description)");
        return getDto(ticketCategoriesRepository.findByEventIdAndType(eventId, description));
    }

    private TicketCategoryDto getDto(TicketCategory ticketCategory) {
        return new TicketCategoryDto(
                ticketCategory.getId(),
                ticketCategory.getDescription(),
                ticketCategory.getPrice()
        );
    }
}
