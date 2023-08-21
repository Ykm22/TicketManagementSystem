using Microsoft.Data.SqlClient;
using TicketManagementSystem_NET_Backend.Models;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory.Database;

namespace TicketManagementSystem_NET_Backend.Repositories.UsersRepository
{
    public class UsersRepository : IUsersRepository
    {
        private readonly string connectionString = @"Data Source=ICHIM\SQLEXPRESS03;Initial Catalog=TicketManagementSystem;Persist Security Info=True;User ID=name;Password=password;TrustServerCertificate=True;encrypt=false;";
        public UsersRepository() { }

        public User Add(User t)
        {
            throw new NotImplementedException();
        }

        public Task<User> Delete(Guid id)
        {
            throw new NotImplementedException();
        }

        public async Task<bool> FindRoleByEmail(string email)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                await connection.OpenAsync();
                using (SqlCommand selectCommand = new SqlCommand(
                    "SELECT isCustomer FROM Users " +
                    "WHERE email = @email", connection))
                {
                    selectCommand.Parameters.AddWithValue("@email", email);
                    using (SqlDataReader reader = await selectCommand.ExecuteReaderAsync())
                    {
                        if (!reader.HasRows)
                        {
                            throw new Exception("User email not found");
                        }
                        reader.Read();
                        return reader.GetBoolean(0);
                    }
                }
            }
        }

        public IEnumerable<User> GetAll()
        {
            throw new NotImplementedException();
        }

        public Task<User> GetById(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<User> Update(User T)
        {
            throw new NotImplementedException();
        }
    }
}