package org.practica.repository;

import jakarta.persistence.LockModeType;
import org.practica.model.Event;
import org.practica.model.TicketCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventsRepository extends CrudRepository<Event, UUID> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Optional<Event> findById(UUID uuid);

    @Override
    @Query("SELECT e FROM Event e " +
            "WHERE " +
                "YEAR(e.startDate) >= YEAR(CURRENT_DATE) " +
                "AND " +
                "MONTH(e.startDate) > MONTH(CURRENT_DATE)" +
//            " AND DAY(e.startDate) between 1 and 30" +
            "")
    Iterable<Event> findAll();
    Page<Event> findAll(Pageable pageable);
    @Query
    Iterable<Event> findByVenueIdAndEventTypeName(UUID venueId, String eventType);

    @Query("SELECT e FROM Event e WHERE YEAR(e.startDate) >= YEAR(CURRENT_DATE) AND MONTH(e.startDate) > MONTH(CURRENT_DATE) " +
//            "AND DAY(e.startDate) between 1 and 30 " +
            "AND e.venue.location = :location AND e.eventType.name = :eventType")
    Iterable<Event> findByVenueLocationAndEventTypeName(String location, String eventType);

}
