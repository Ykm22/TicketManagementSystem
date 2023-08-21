package org.practica.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Venues")
public class Venue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID venuesId;

    @Column(name = "Location", nullable = false)
    private String location;

    @Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "Capacity")
    private Integer capacity;

    @Column(name = "PricePerHour", precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @OneToMany(mappedBy = "venue")
    private List<Event> events;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Version
    @Column(name = "version")
    private Long version;
    public Venue() {
    }

    public Venue(String location, String type) {
        this.location = location;
        this.type = type;
    }

    public UUID getId() {
        return venuesId;
    }

    public void setId(UUID venuesId) {
        this.venuesId = venuesId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
