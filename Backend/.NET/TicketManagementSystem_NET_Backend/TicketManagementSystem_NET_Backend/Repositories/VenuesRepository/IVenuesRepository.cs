using Microsoft.Data.SqlClient;
using TicketManagementSystem_NET_Backend.Models;

namespace TicketManagementSystem_NET_Backend.Repositories.NewFolder
{
    public interface IVenuesRepository : IRepository<Venue>
    {
        Task<int> ADO_GetCapacityById(SqlConnection connection, SqlTransaction transaction, Guid guid);
        Task<int> GetCapacityById(Guid? venueId);
    }
}
