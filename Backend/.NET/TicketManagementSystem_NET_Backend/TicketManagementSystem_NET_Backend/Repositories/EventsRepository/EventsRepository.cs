using Microsoft.Data.SqlClient;
using TicketManagementSystem_NET_Backend.Models;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory.Database;

namespace TicketManagementSystem_NET_Backend.Repositories.EventsRepository
{
    public class EventsRepository : IEventsRepository
    {
        private TicketManagementSystemContext _dbContext;
        private readonly string connectionString = @"Data Source=ICHIM\SQLEXPRESS03;Initial Catalog=TicketManagementSystem;Persist Security Info=True;User ID=name;Password=password;TrustServerCertificate=True;encrypt=false;";

        public EventsRepository()
        {
            _dbContext = new TicketManagementSystemContext();
        }

        public Event Add(Event @event)
        {
            throw new NotImplementedException();
        }

        public Task<Event> Delete(Guid id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Event> GetAll()
        {
            throw new NotImplementedException();
        }

        public Task<Event> GetById(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<Event> Update(Event @event)
        {
            throw new NotImplementedException();
        }

        public async Task<Event> ADO_GetById(Guid eventId)
        {
            using(SqlConnection connection = new SqlConnection(connectionString))
            {
                await connection.OpenAsync();
                using (SqlCommand selectCommand = new SqlCommand("SELECT * FROM Events WHERE ID = @EventId", connection))
                {
                    selectCommand.Parameters.AddWithValue("EventId", eventId);
                    using (SqlDataReader reader = selectCommand.ExecuteReader())
                    {
                        if (!reader.HasRows)
                        {
                            throw new Exception("No event found");
                        }
                        await reader.ReadAsync();
                        return ConstructEventFromReader(reader);
                    }
                }
            }
        }
        private Event ConstructEventFromReader(SqlDataReader reader)
        {
            Event @event = new Event
            {
                Id = reader.GetGuid(0),
                VenueId = reader.GetGuid(1),
                EventTypeId = reader.GetGuid(2),
                Description = reader.GetString(3),
                Name = reader.GetString(4),
                StartDate = reader.GetDateTime(5),
                EndDate = reader.GetDateTime(6)
            };
            return @event;
        }
    }
}
