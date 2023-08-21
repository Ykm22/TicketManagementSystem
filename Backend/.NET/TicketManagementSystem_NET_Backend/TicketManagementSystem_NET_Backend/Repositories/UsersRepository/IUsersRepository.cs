using TicketManagementSystem_NET_Backend.Models;

namespace TicketManagementSystem_NET_Backend.Repositories.UsersRepository
{
    public interface IUsersRepository : IRepository<User>
    {
        Task<bool> FindRoleByEmail(string email);
    }
}