package org.practica.repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.practica.model.Event;
import org.practica.model.Order;
import org.practica.model.TicketCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdersRepository extends CrudRepository<Order, UUID> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    <S extends Order> S save(S entity);

    @Override
    Optional<Order> findById(UUID uuid);

    @Query
    Optional<Iterable<Order>> findAllByUserId(UUID userId);

    @Query("SELECT o FROM Order o WHERE o.ticketCategory in :ticketCategories")
//    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    List<Order> findAllByTicketCategories(@Param("ticketCategories")List<TicketCategory> ticketCategoryList);

    @Query("SELECT o FROM Order o WHERE o.ticketCategory.ticketCategoryId = :ticketCategoryId")
    List<Order> findTop10ByTicketCategoryId(@Param("ticketCategoryId") UUID ticketCategoryId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.ticketCategory.ticketCategoryId = :ticketCategoryId")
    List<Order> findAllByTicketCategoryId(UUID ticketCategoryId);

}
