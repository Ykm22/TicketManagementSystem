package org.practica.controllers;

import org.practica.model.TicketCategory;
import org.practica.model.dto.TicketCategoryDto;
import org.practica.service.ticket_categories_service.TicketCategoriesService;
import org.practica.service.ticket_categories_service.TicketCategoriesServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("/tms/api/spring/ticketcategories")
public class TicketCategoryController {
    private final TicketCategoriesService ticketCategoriesService;

    public TicketCategoryController(TicketCategoriesServiceImpl ticketCategoriesService){
        this.ticketCategoriesService = ticketCategoriesService;
    }

    @GetMapping(value = "/{ticketCategoryId}")
    public TicketCategory getTicketCategory(@PathVariable UUID ticketCategoryId){
        return ticketCategoriesService.getTicketCategoryById(ticketCategoryId);
    }

    @GetMapping()
    public Iterable<TicketCategory> getTicketCategoriesByEventId(@RequestParam UUID eventId){
        return ticketCategoriesService.getTicketCategoriesByEventId(eventId);
    }

    @GetMapping(params = {"eventId", "description"})
    public TicketCategoryDto getTicketCategoryByEventAndType(@RequestParam UUID eventId, @RequestParam String description){
        return ticketCategoriesService.getTicketCategoryIdByEventAndType(eventId, description);
    }
}
