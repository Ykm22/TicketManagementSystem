package org.practica.service.ticket_categories_service;

import org.practica.model.TicketCategory;
import org.practica.model.dto.TicketCategoryDto;

import java.util.UUID;

public interface TicketCategoriesService {
    TicketCategory getTicketCategoryById(UUID ticketCategoryId);

    Iterable<TicketCategory> getTicketCategoriesByEventId(UUID eventId);

    TicketCategoryDto getTicketCategoryIdByEventAndType(UUID eventId, String description);
}
