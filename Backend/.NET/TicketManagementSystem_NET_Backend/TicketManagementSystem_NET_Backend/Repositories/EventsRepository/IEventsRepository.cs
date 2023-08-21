using TicketManagementSystem_NET_Backend.Models;

namespace TicketManagementSystem_NET_Backend.Repositories.EventsRepository
{
    public interface IEventsRepository : IRepository<Event>
    {
        Task<Event> ADO_GetById(Guid eventId);
    }
}
