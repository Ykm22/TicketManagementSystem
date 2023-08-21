package org.practica.service.orders_service;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.hibernate.StaleObjectStateException;
import org.practica.exceptions.ErrorCreatingOrderException;
import org.practica.exceptions.InsufficientTicketsException;
import org.practica.exceptions.TicketCategoryNotFoundException;
import org.practica.exceptions.UserNotFoundException;
import org.practica.model.Order;
import org.practica.model.dto.OrderDto;
import org.practica.model.dto.OrderDto2;
import org.practica.model.httpMethodsInput.OrderPOST;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersService {
    OrderDto2 createOrder(OrderPOST orderPOST, UUID customerId) throws
//            Exception,
            ObjectOptimisticLockingFailureException,
            ErrorCreatingOrderException,
            InsufficientTicketsException, TicketCategoryNotFoundException, UserNotFoundException, ErrorCreatingOrderException;
    Order saveOrder(Order order);
    Order getOrderById(UUID orderId);
    Iterable<Order> getOrdersByUserId(UUID userId);
    List<Order> getOrdersByTicketCategoryId(UUID ticketCategoryId);

}
