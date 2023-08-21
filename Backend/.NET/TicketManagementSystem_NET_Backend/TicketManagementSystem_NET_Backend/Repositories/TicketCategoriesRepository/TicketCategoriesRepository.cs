using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using TicketManagementSystem_NET_Backend.Models;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory.Database;

namespace TicketManagementSystem_NET_Backend.Repositories.TicketCategoriesRepository
{
    public class TicketCategoriesRepository : ITicketCategoriesRepository
    {
        private readonly TicketManagementSystemContext _dbContext;
        private readonly string connectionString = @"Data Source=ICHIM\SQLEXPRESS03;Initial Catalog=TicketManagementSystem;Persist Security Info=True;User ID=name;Password=password;TrustServerCertificate=True;encrypt=false;";
        public TicketCategoriesRepository()
        {
            _dbContext = new TicketManagementSystemContext();
        }
        public TicketCategory Add(TicketCategory t)
        {
            throw new NotImplementedException();
        }

        public async Task<List<TicketCategory>> ADO_FindByEventId(Guid id)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                await connection.OpenAsync();
                using (SqlCommand selectCommand = new SqlCommand("" +
                    "SELECT * FROM TicketCategories " +
                    "WHERE Event_ID = @EventId " +
                    "ORDER BY ID", connection))
                {
                    selectCommand.Parameters.AddWithValue("EventId", id);
                    using (SqlDataReader reader = selectCommand.ExecuteReader())
                    {
                        if (!reader.HasRows)
                        {
                            throw new Exception("Ticket categories found");
                        }
                        List<TicketCategory> ticketCategories = new();
                        while (await reader.ReadAsync())
                        {
                            ticketCategories.Add(ConstructTicketCategoryFromReader(reader));
                        }
                        return ticketCategories;
                    }
                }
            }
        }
        private TicketCategory ConstructTicketCategoryFromReader(SqlDataReader reader)
        {
            TicketCategory ticketCategory = new TicketCategory
            {
                Id = reader.GetGuid(0),
                EventId = reader.GetGuid(1),
                Description = reader.GetString(2),
                Price = reader.GetDecimal(3)
            };
            return ticketCategory;
        }

        public async Task<Guid> ADO_GetEventIdById(Guid guid)
        {
            using(SqlConnection connection = new SqlConnection(connectionString))
            {
                await connection.OpenAsync();
                using (SqlCommand selectCommand = new SqlCommand("SELECT * FROM TicketCategories WHERE ID = @TicketCategories_ID", connection))
                {
                    selectCommand.Parameters.AddWithValue("TicketCategories_ID", guid);
                    using (SqlDataReader reader = await selectCommand.ExecuteReaderAsync())
                    {
                        if (!reader.HasRows)
                        {
                            throw new Exception("Ticket Category not found");
                        }
                        await reader.ReadAsync();
                        return reader.GetGuid(1);
                    }
                }
            }
        }

        public Task<TicketCategory> Delete(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<List<TicketCategory>> FindByEventId(Guid id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<TicketCategory> GetAll()
        {
            throw new NotImplementedException();
        }

        public async Task<TicketCategory> GetByEventIdAndDescription(Guid eventId, string description)
        {
            return await _dbContext.TicketCategories
                .Where(tc => tc.EventId == eventId && tc.Description == description)
                .FirstOrDefaultAsync()
                ??
                throw new Exception("Ticket category not found");
        }

        public Task<TicketCategory> GetById(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<TicketCategory> Update(TicketCategory T)
        {
            throw new NotImplementedException();
        }
    }
}
