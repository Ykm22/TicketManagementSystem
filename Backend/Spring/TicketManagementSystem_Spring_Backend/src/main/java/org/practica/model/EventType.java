package org.practica.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "EventTypes")
public class EventType implements Serializable {
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID eventTypesId;

    @Column(name = "Name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "eventType")
    private List<Event> events;
    @Version
    @Column(name = "version")
    private Long version;
    public EventType() {
    }

    public EventType(String name) {
        this.name = name;
    }

    public UUID getId() {
        return eventTypesId;
    }

    public void setId(UUID eventTypesId) {
        this.eventTypesId = eventTypesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
