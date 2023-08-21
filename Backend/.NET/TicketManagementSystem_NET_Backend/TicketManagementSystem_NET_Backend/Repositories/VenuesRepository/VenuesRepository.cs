using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Logging;
using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Repositories.NewFolder;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory.Database;

namespace TicketManagementSystem_NET_Backend.Repositories.VenuesRepository
{
    public class VenuesRepository : IVenuesRepository
    {
        private readonly string connectionString = @"Data Source=ICHIM\SQLEXPRESS03;Initial Catalog=TicketManagementSystem;Persist Security Info=True;User ID=name;Password=password;TrustServerCertificate=True;encrypt=false;";
        public Venue Add(Venue t)
        {
            throw new NotImplementedException();
        }

        public Task<Venue> Delete(Guid id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Venue> GetAll()
        {
            throw new NotImplementedException();
        }

        public Task<Venue> GetById(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<int> GetCapacityById(Guid? venueId)
        {
            throw new NotImplementedException();
        }

        public async Task<int> ADO_GetCapacityById(SqlConnection connection, SqlTransaction transaction, Guid venueId)
        {
            using (SqlCommand selectCommand = new SqlCommand("SELECT Capacity FROM Venues WHERE ID = @VenueId", connection, transaction))
            {
                selectCommand.Parameters.AddWithValue("VenueId", venueId);
                using (SqlDataReader reader = selectCommand.ExecuteReader())
                {
                    if (!reader.HasRows)
                    {
                        throw new Exception("No venue found");
                    }
                    await reader.ReadAsync();
                    return reader.GetInt32(0);
                }
            }
        }

        public Task<Venue> Update(Venue T)
        {
            throw new NotImplementedException();
        }
    }
}
