using Microsoft.AspNetCore.DataProtection.KeyManagement.Internal;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using System.Transactions;
using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Models.Dto;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory.Database;

namespace TicketManagementSystem_NET_Backend.Repositories.OrdersRepository
{
    public class OrdersRepository : IOrdersRepository
    {
        private TicketManagementSystemContext _dbContext;
        private readonly string connectionString = @"Data Source=ICHIM\SQLEXPRESS03;Initial Catalog=TicketManagementSystem;Persist Security Info=True;User ID=name;Password=password;TrustServerCertificate=True;encrypt=false;";

        public OrdersRepository()
        {
            _dbContext = new TicketManagementSystemContext();   
        }
        public Order Add(Order t)
        {
            throw new NotImplementedException();
        }

        public async Task<Order> Delete(Guid id)
        {
            Order orderToDelete = await GetById(id);
            
            if (orderToDelete != null)
            {
                 _dbContext.Orders.Remove(orderToDelete);
                await _dbContext.SaveChangesAsync();
                return orderToDelete;
            }
            throw new Exception("Order not found");
        }

        public IEnumerable<Order> FilterByUserId(Guid userId)
        {
            return _dbContext.Orders
                .Where(o => o.UserId == userId)
                .ToList();
        }

        public IEnumerable<Order> GetAll()
        {
            throw new NotImplementedException();
        }

        public async Task<Order> GetById(Guid id)
        {
            return await _dbContext.Orders
                .Where(o => o.OrderId == id)
                .FirstOrDefaultAsync() 
                ?? 
                throw new Exception("Order not found");
        }

        public async Task<Order> GetById_EagerTicketCategoriesLoading(Guid orderId)
        {
            return await _dbContext.Orders
                .Where(o => o.OrderId == orderId)
                .Include(o => o.TicketCategories)
                .FirstOrDefaultAsync()
                ??
                throw new Exception("Order not found");
        }
        public async Task<Order> Update(Order order)
        {
            _dbContext.Update(order);
            await _dbContext.SaveChangesAsync();
            return order;
        }

        public async Task<Order> ADO_Update(SqlConnection connection, SqlTransaction transaction, Guid orderId, Guid ticketCategoryId, int numberOfTickets)
        {
            using (SqlCommand updateCommand = new SqlCommand("UPDATE Orders " +
                "SET TicketCategories_ID = @TicketCategoryId, NumberOfTickets = @NumberOfTickets, OrderedAt = @OrderedAt " +
                "WHERE ID = @OrderId", connection, transaction))
            {
                updateCommand.Parameters.AddWithValue("@OrderId", orderId);
                updateCommand.Parameters.AddWithValue("@TicketCategoryId", ticketCategoryId);
                updateCommand.Parameters.AddWithValue("@NumberOfTickets", numberOfTickets);
                var orderedAt = DateTime.Now;
                updateCommand.Parameters.AddWithValue("@OrderedAt", orderedAt);

                int rowsAffected = await updateCommand.ExecuteNonQueryAsync();

                if (rowsAffected > 0)
                {
                    return new Order
                    {
                        OrderId = orderId,
                        TicketCategoryId = ticketCategoryId,
                        NumberOfTickets = numberOfTickets,
                        OrderedAt = orderedAt
                    };
                }
                else
                {
                    throw new Exception("Order not found or not updated.");
                }
            }
        }

        private Order ConstructOrderFromReader(bool task, SqlDataReader reader)
        {
            Order order = new Order
            {
                OrderId = reader.GetGuid(0),
                UserId = reader.GetGuid(1),
                TicketCategoryId = reader.GetGuid(2),
                OrderedAt = reader.GetDateTime(3),
                NumberOfTickets = reader.GetInt32(4),
                TotalPrice = reader.GetInt32(5)
            };
            int x = 0;
            return order;
        }
        public async Task<Order> ADO_GetById(Guid orderId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                await connection.OpenAsync();
                using (SqlCommand selectCommand = new SqlCommand("SELECT * FROM Orders WHERE ID = @OrderId", connection))
                {
                    selectCommand.Parameters.AddWithValue("OrderId", orderId);
                    using (SqlDataReader reader = await selectCommand.ExecuteReaderAsync())
                    {
                        if (!reader.HasRows)
                        {
                            throw new Exception("Order not found");
                        }
                        return ConstructOrderFromReader(await reader.ReadAsync(), reader);
                    }
                }
            }
        }

        public async Task<int> ADO_GetNumberOfTicketsForTicketCategories(SqlConnection connection, 
            SqlTransaction transaction, List<TicketCategory> ticketCategories)
        {
            int boughtTickets = 0;
            foreach (TicketCategory ticketCategory in ticketCategories)
            {
                using (SqlCommand selectCommand = new SqlCommand(
                    "SELECT NumberOfTickets FROM Orders " +
                    "WITH (XLOCK) " +
                    "WHERE TicketCategories_ID = @TicketCategories_ID", 
                    connection, transaction))
                {
                    selectCommand.Parameters.AddWithValue("TicketCategories_ID", ticketCategory.Id);
                    using (SqlDataReader reader = selectCommand.ExecuteReader())
                    {
                        if (reader.HasRows)
                        {
                            while (await reader.ReadAsync())
                            {
                                int orderBoughtTickets = reader.GetInt32(0);
                                boughtTickets += orderBoughtTickets;
                            }
                        }
                    }
                }
            }
            return boughtTickets;
        }

        public async Task<Order> ADO_Add(SqlConnection connection, SqlTransaction transaction, OrderDtoPost orderPostDto, Guid customerId, int totalPrice)
        {
            using (SqlCommand insertCommand = new SqlCommand("" +
                "INSERT INTO Orders (ID, User_ID, TicketCategories_ID, OrderedAt, NumberOfTickets, TotalPrice)" +
                "VALUES (@ID, @User_ID, @TicketCategories_ID, @OrderedAt, @NumberOfTickets, @TotalPrice)", 
                connection, transaction))
            {
                Guid orderId = Guid.NewGuid();
                insertCommand.Parameters.AddWithValue("@ID", orderId);
                insertCommand.Parameters.AddWithValue("@User_ID", customerId);
                insertCommand.Parameters.AddWithValue("@TicketCategories_ID", orderPostDto.TicketCategoryId);
                insertCommand.Parameters.AddWithValue("@NumberOfTickets", orderPostDto.NumberOfTickets);
                insertCommand.Parameters.AddWithValue("@TotalPrice", totalPrice);
                var orderedAt = DateTime.Now;
                insertCommand.Parameters.AddWithValue("@OrderedAt", orderedAt);

                int rowsAffected = await insertCommand.ExecuteNonQueryAsync();

                if (rowsAffected > 0)
                {
                    return new Order
                    {
                        OrderId = orderId,
                        UserId = customerId,
                        TicketCategoryId = orderPostDto.TicketCategoryId,
                        OrderedAt = orderedAt,
                        NumberOfTickets = orderPostDto.NumberOfTickets,
                        TotalPrice = totalPrice,
                    };
                }
                else
                {
                    throw new Exception("Order not saved.");
                }
            }
        }

        public Task<Order> ADO_Update_SQL(Guid orderId, Guid newTicketCategoryId, int numberOfTickets, decimal totalPrice)
        {
            //using (SqlConnection connection = new SqlConnection(connectionString))
            //{
            //    using (SqlCommand updateCommand = new SqlCommand(
            //        "UPDATE Orders " +
            //        "SET " +
            //            "TicketCategories_ID = @TicketCategoryId, " +
            //            "NumberOfTickets = @NumberOfTickets, " +
            //            "OrderedAt = @OrderedAt, " +
            //            "TotalPrice = @TotalPrice " +
            //        "WHERE ID = @OrderId", connection))
            //    {
            //        updateCommand.Parameters.AddWithValue("@OrderId", orderId);
            //        updateCommand.Parameters.AddWithValue("@TicketCategoryId", ticketCategoryId);
            //        updateCommand.Parameters.AddWithValue("@NumberOfTickets", numberOfTickets);
            //        var orderedAt = DateTime.Now;
            //        updateCommand.Parameters.AddWithValue("@OrderedAt", orderedAt);

            //        int rowsAffected = await updateCommand.ExecuteNonQueryAsync();

            //        if (rowsAffected > 0)
            //        {
            //            return new Order
            //            {
            //                OrderId = orderId,
            //                TicketCategoryId = ticketCategoryId,
            //                NumberOfTickets = numberOfTickets,
            //                OrderedAt = orderedAt
            //            };
            //        }
            //        else
            //        {
            //            throw new Exception("Order not found or not updated.");
            //        }
            //    }
            //}
            return null;
        }

        public async Task<Order> ADO_Add_SQL(
            OrderDtoPost orderPostDto, Guid customerId, int totalPrice,
            Event @event, List<TicketCategory> ticketCategories)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                await connection.OpenAsync();
                string sqlInsertQuery = @"
                    INSERT INTO Orders (ID, User_ID, TicketCategories_ID, OrderedAt, NumberOfTickets, TotalPrice)
                    SELECT @OrderId,
                           @UserID,
                           @TicketCategoryID,
                           @OrderedAt,
                           @NumberOfTickets,
                           @TotalPrice
                    WHERE
                        @NumberOfTickets <= (
                            SELECT V.Capacity - ISNULL(SUM(O.NumberOfTickets), 0) AS AvailableTickets
                            FROM Orders O
                            INNER JOIN Events E ON E.ID = @EventID
                            INNER JOIN Venues V ON V.ID = E.Venue_ID
                            WHERE O.TicketCategories_ID IN(@TicketCategoryID1, @TicketCategoryID2)
                            GROUP BY E.Venue_ID, V.Capacity
                        );
                ";
                using (SqlCommand insertCommand = new SqlCommand(sqlInsertQuery, connection))
                {
                    Guid orderId = Guid.NewGuid();
                    insertCommand.Parameters.AddWithValue("@OrderId", orderId);
                    insertCommand.Parameters.AddWithValue("@UserId", customerId);
                    insertCommand.Parameters.AddWithValue("@TicketCategoryID", orderPostDto.TicketCategoryId);

                    var orderedAt = DateTime.Now;
                    insertCommand.Parameters.AddWithValue("@OrderedAt", orderedAt);
                    insertCommand.Parameters.AddWithValue("@NumberOfTickets", orderPostDto.NumberOfTickets);
                    insertCommand.Parameters.AddWithValue("@TotalPrice", totalPrice);
                    insertCommand.Parameters.AddWithValue("@EventID", @event.Id);
                    insertCommand.Parameters.AddWithValue("TicketCategoryID1", ticketCategories[0].Id);
                    insertCommand.Parameters.AddWithValue("TicketCategoryID2", ticketCategories[1].Id);

                    int rowsAffected = await insertCommand.ExecuteNonQueryAsync();

                    if (rowsAffected > 0)
                    {
                        return new Order
                        {
                            OrderId = orderId,
                            UserId = customerId,
                            TicketCategoryId = orderPostDto.TicketCategoryId,
                            OrderedAt = orderedAt,
                            NumberOfTickets = orderPostDto.NumberOfTickets,
                            TotalPrice = totalPrice,
                        };
                    }
                    else
                    {
                        throw new Exception("Order not found or not saved.");
                    }
                }
            }
        }
    }
}
