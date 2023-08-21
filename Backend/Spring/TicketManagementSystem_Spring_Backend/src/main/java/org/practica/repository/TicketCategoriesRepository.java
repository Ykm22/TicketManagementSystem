package org.practica.repository;

import jakarta.persistence.LockModeType;
import org.practica.model.TicketCategory;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketCategoriesRepository extends CrudRepository<TicketCategory, UUID> {
    @Override
//    @Lock(LockModeType.OPTIMISTIC)
    Optional<TicketCategory> findById(UUID uuid);

    @Query
    Iterable<TicketCategory> findAllByEventId(UUID eventId);
    @Query("SELECT tc FROM TicketCategory tc WHERE tc.event.eventId = :eventId AND tc.description = :description")
    TicketCategory findByEventIdAndType(UUID eventId, String description);
}
