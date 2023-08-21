using Microsoft.Data.SqlClient;
using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Models.Dto;

namespace TicketManagementSystem_NET_Backend.Repositories.OrdersRepository
{
    public interface IOrdersRepository : IRepository<Order>
    {
        Task<Order> ADO_Add(SqlConnection connection, SqlTransaction transaction, OrderDtoPost orderPostDto, Guid customerId, int totalPrice);
        Task<Order> ADO_Add_SQL(OrderDtoPost orderPostDto, Guid customerId, int totalPrice, Event @event, List<TicketCategory> ticketCategories);
        Task<Order> ADO_GetById(Guid orderId);
        Task<int> ADO_GetNumberOfTicketsForTicketCategories(SqlConnection connection, SqlTransaction transaction, List<TicketCategory> ticketCategories);
        Task<Order> ADO_Update(SqlConnection connection, SqlTransaction transaction, Guid orderId, Guid newTicketCategoryId, int numberOfTickets);
        Task<Order> ADO_Update_SQL(Guid orderId, Guid newTicketCategoryId, int numberOfTickets, decimal totalPrice);
        IEnumerable<Order> FilterByUserId(Guid userId);
        Task<Order> GetById_EagerTicketCategoriesLoading(Guid orderId);
    }
}
