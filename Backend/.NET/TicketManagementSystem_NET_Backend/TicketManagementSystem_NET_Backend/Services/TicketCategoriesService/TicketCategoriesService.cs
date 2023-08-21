using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Repositories.TicketCategoriesRepository;

namespace TicketManagementSystem_NET_Backend.Services.TicketCategoriesService
{
    public class TicketCategoriesService : ITicketCategoriesService
    {
        private readonly ITicketCategoriesRepository _ticketCategoriesRepository;

        public TicketCategoriesService(ITicketCategoriesRepository ticketCategoriesRepository)
        {
            _ticketCategoriesRepository = ticketCategoriesRepository;
        }

        public async Task<TicketCategory> GetTicketCategory_ByEventIdAndDescription(Guid eventId, string description)
        {
            return await _ticketCategoriesRepository.GetByEventIdAndDescription(eventId, description);
        }
    }
}
