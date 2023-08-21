using TicketManagementSystem_NET_Backend.Models;

namespace TicketManagementSystem_NET_Backend.Repositories
{
    public interface IRepository<T> 
    {
        IEnumerable<T> GetAll();
        Task<T> GetById(Guid id);
        T Add(T t);
        Task<T> Update(T T);
        Task<T> Delete(Guid id);
    }
}
