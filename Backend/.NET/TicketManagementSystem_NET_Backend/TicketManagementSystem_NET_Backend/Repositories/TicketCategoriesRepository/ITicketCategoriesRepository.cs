using TicketManagementSystem_NET_Backend.Models;

namespace TicketManagementSystem_NET_Backend.Repositories.TicketCategoriesRepository
{
    public interface ITicketCategoriesRepository : IRepository<TicketCategory>
    {
        Task<List<TicketCategory>> ADO_FindByEventId(Guid id);
        Task<Guid> ADO_GetEventIdById(Guid guid);
        Task<List<TicketCategory>> FindByEventId(Guid id);
        Task<TicketCategory> GetByEventIdAndDescription(Guid eventId, string description);
    }
}
