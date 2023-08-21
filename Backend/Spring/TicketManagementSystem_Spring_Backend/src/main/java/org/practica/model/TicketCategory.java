package org.practica.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TicketCategories")
public class TicketCategory implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "ID")
    private UUID ticketCategoryId;

    @ManyToOne
    @JoinColumn(name = "Event_ID", referencedColumnName = "id")
    private Event event;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public TicketCategory() {
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Version
    @Column(name = "version")
    private Long version;
    public TicketCategory(Event event, String description, BigDecimal price) {
        this.event = event;
        this.description = description;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketCategory that)) return false;
        return Objects.equals(ticketCategoryId, that.ticketCategoryId) && Objects.equals(getEvent(), that.getEvent()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketCategoryId, getEvent(), getDescription(), getPrice(), getVersion());
    }

    public UUID getId() {
        return ticketCategoryId;
    }

    @Override
    public String toString() {
        return "TicketCategory{" +
                "ticketCategoryId=" + ticketCategoryId +
                ", eventId=" + event.getId() +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    public void setId(UUID ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}