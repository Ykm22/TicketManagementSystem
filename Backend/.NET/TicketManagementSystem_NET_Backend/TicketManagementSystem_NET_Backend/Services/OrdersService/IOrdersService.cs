using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Models.Dto;

namespace TicketManagementSystem_NET_Backend.Services.OrdersService
{
    public interface IOrdersService
    {
        Task<Order> Delete(Guid orderId);
        Task<Order> GetOrder(Guid orderId);
        IEnumerable<Order> GetOrdersByUserId(Guid userId);
        Task<Order> UpdateTicketCategoryAndNumberOfTickets(Guid orderId, TicketDto ticketDto);
        Task<(Order, Guid)> ADO_Update(Guid orderId, TicketDto ticketDto);
        Task<(Order, Guid)> ADO_Update_SQL(Guid orderId, TicketDto ticketDto);
        Task<(Order, string)> ADO_Save_SQL(OrderDtoPost orderPostDto, Guid customerId);
    }
}
