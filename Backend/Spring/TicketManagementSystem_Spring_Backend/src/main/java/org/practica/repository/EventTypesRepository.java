package org.practica.repository;

import org.practica.model.EventType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventTypesRepository extends CrudRepository<EventType, UUID> {
}
