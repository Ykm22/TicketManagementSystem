package org.practica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID ordersId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "User_ID", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TicketCategories_ID", referencedColumnName = "id")
    private TicketCategory ticketCategory;

    @Column(name = "OrderedAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderedAt;

    @Column(name = "NumberOfTickets", nullable = false)
    private int numberOfTickets;

    @Column(name = "TotalPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    public Order() {
    }

    public Order(User user, TicketCategory ticketCategory, Date orderedAt, int numberOfTickets, BigDecimal totalPrice) {
        this.user = user;
        this.ticketCategory = ticketCategory;
        this.orderedAt = orderedAt;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
    }
    @Version
    @Column(name = "version")
    private Long version;
    public UUID getId() {
        return ordersId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setId(UUID ordersId) {
        this.ordersId = ordersId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TicketCategory getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(TicketCategory ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ordersId=" + ordersId +
                ", userId=" + user.getId() +
                ", ticketCategoryId=" + ticketCategory.getId() +
                ", orderedAt=" + orderedAt +
                ", numberOfTickets=" + numberOfTickets +
                ", totalPrice=" + totalPrice +
                '}';
    }
}