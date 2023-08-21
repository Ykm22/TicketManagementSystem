using TicketManagementSystem_NET_Backend.Models;

namespace TicketManagementSystem_NET_Backend.Services.TicketCategoriesService
{
    public interface ITicketCategoriesService
    {
        Task<TicketCategory> GetTicketCategory_ByEventIdAndDescription(Guid eventId, string description);
    }
}
