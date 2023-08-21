using Microsoft.AspNetCore.Connections.Features;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Logging;
using System.Data;
using System.Linq.Expressions;
using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Models.Dto;
using TicketManagementSystem_NET_Backend.Repositories.EventsRepository;
using TicketManagementSystem_NET_Backend.Repositories.NewFolder;
using TicketManagementSystem_NET_Backend.Repositories.OrdersRepository;
using TicketManagementSystem_NET_Backend.Repositories.TicketCategoriesRepository;

namespace TicketManagementSystem_NET_Backend.Services.OrdersService
{
    public class OrdersService : IOrdersService
    {
        private readonly IOrdersRepository _ordersRepository;
        private readonly ITicketCategoriesRepository _ticketCategoriesRepository;
        private readonly IEventsRepository _eventsRepository;
        private readonly IVenuesRepository _venuesRepository;
        private readonly string connectionString = @"Data Source=ICHIM\SQLEXPRESS03;Initial Catalog=TicketManagementSystem;Persist Security Info=True;User ID=name;Password=password;TrustServerCertificate=True;encrypt=false;";

        public OrdersService(
            IOrdersRepository ordersRepository,
            ITicketCategoriesRepository ticketCategoriesRepository,
            IEventsRepository eventsRepository,
            IVenuesRepository venuesRepository)
        {
            _ordersRepository = ordersRepository;
            _ticketCategoriesRepository = ticketCategoriesRepository;
            _eventsRepository = eventsRepository;
            _venuesRepository = venuesRepository;

        }
        public async Task<(Order, string)> ADO_Save_SQL(OrderDtoPost orderPostDto, Guid customerId)
        {
            Event @event = await _eventsRepository.ADO_GetById(orderPostDto.EventId);
            List<TicketCategory> ticketCategories = await _ticketCategoriesRepository.ADO_FindByEventId(@event.Id);
            (decimal totalPrice, string description) = GetTotalPrice(ticketCategories, orderPostDto);
            Thread.Sleep(5000);
            return (
                await _ordersRepository.ADO_Add_SQL(orderPostDto, customerId, (int)totalPrice, @event, ticketCategories), 
                description
           );
        }
        public async Task<(Order, string)> ADO_Save(OrderDtoPost orderPostDto, Guid customerId)
        {
            Event @event = await _eventsRepository.ADO_GetById(orderPostDto.EventId);
            List<TicketCategory> ticketCategories = await _ticketCategoriesRepository.ADO_FindByEventId(@event.Id);
            (decimal totalPrice, string description) = GetTotalPrice(ticketCategories, orderPostDto);
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                await connection.OpenAsync();
                int retryCount = 1, maxRetries = 10;
                using (SqlTransaction transaction = connection.BeginTransaction())
                {
                    while (retryCount <= maxRetries)
                    {
                        try
                        {
                            //READ
                            int availableTickets = await GetAvailableTickets(connection, transaction, ticketCategories, @event);
                            //Thread.Sleep(3000);
                            if (availableTickets < orderPostDto.NumberOfTickets)
                            {
                                throw new IllegalTicketsAmountException("Not enough tickets available");
                            }

                            //CREATE
                            Order order = await _ordersRepository.ADO_Add(connection, transaction, orderPostDto, customerId, (int)totalPrice);
                            transaction.Commit();

                            return (order, description);
                        }
                        catch (IllegalTicketsAmountException ex)
                        {
                            //await Console.Out.WriteLineAsync("hehe");
                            throw new Exception(ex.Message);
                        }
                        catch (Exception ex)
                        {
                            //Console.WriteLine(ex.Message);
                            retryCount++;
                        }
                    }
                    await transaction.RollbackAsync();
                    throw new Exception("Maximum retries reached for transaction");
                }
            }
        }

        private (decimal, string) GetTotalPrice(List<TicketCategory> ticketCategories, OrderDtoPost orderPostDto)
        {
            foreach(TicketCategory ticketCategory in ticketCategories)
            {
                if(ticketCategory.Id == orderPostDto.TicketCategoryId)
                {
                    return (
                        ticketCategory.Price * (decimal)orderPostDto.NumberOfTickets,
                        ticketCategory.Description
                        );
                }
            }
            throw new NoTicketCategoryFoundException("No ticket category found for requested id");
        }

        public async Task<(Order, Guid)> ADO_Update(Guid orderId, TicketDto ticketDto)
        {
            Order orderToUpdate = await _ordersRepository.ADO_GetById(orderId);
            Guid eventId = await _ticketCategoriesRepository.ADO_GetEventIdById(orderToUpdate?.TicketCategoryId ?? Guid.Empty);
            Event @event = await _eventsRepository.ADO_GetById(eventId);
            List<TicketCategory> ticketCategories = await _ticketCategoriesRepository.ADO_FindByEventId(@event.Id);
            (Guid newTicketCategoryId, decimal totalPrice) = GetNewTicketCategoryIdByDescription(ticketCategories, ticketDto);
            
            using(SqlConnection  connection = new SqlConnection(connectionString))
            {
                connection.Open();
                int retryCount = 1, maxRetries = 10;
                while (retryCount <= maxRetries)
                {
                    using (SqlTransaction transaction = connection.BeginTransaction())
                    {
                        try
                        {
                            // READ
                            int availableTickets = await GetAvailableTickets(connection, transaction, ticketCategories, @event);
                            if (availableTickets + orderToUpdate.NumberOfTickets < ticketDto.NumberOfTickets)
                            {
                                throw new IllegalTicketsAmountException("Cannot modify to requested tickets");
                            }
                            // UPDATE
                            Order order = await _ordersRepository.ADO_Update(connection, transaction, orderId, newTicketCategoryId, ticketDto.NumberOfTickets);
                            order.TotalPrice = (int)totalPrice;

                            transaction.Commit();
                            return (order, @event.Id);

                        }
                        catch (IllegalTicketsAmountException ex)
                        {
                            throw new Exception(ex.Message);
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine(ex.Message);
                            transaction.Rollback();
                            retryCount++;
                        }
                    }
                }
                throw new Exception("Maximum retries reached");
            }
        }
        public async Task<(Order, Guid)> ADO_Update_SQL(Guid orderId, TicketDto ticketDto)
        {
            //Order orderToUpdate = await _ordersRepository.ADO_GetById(orderId);
            //Guid eventId = await _ticketCategoriesRepository.ADO_GetEventIdById(orderToUpdate?.TicketCategoryId ?? Guid.Empty);
            //Event @event = await _eventsRepository.ADO_GetById(eventId);
            //List<TicketCategory> ticketCategories = await _ticketCategoriesRepository.ADO_FindByEventId(@event.Id);
            //(Guid newTicketCategoryId, decimal totalPrice) = GetNewTicketCategoryIdByDescription(ticketCategories, ticketDto);
            //Order order = await _ordersRepository.ADO_Update_SQL(orderId, newTicketCategoryId, ticketDto.NumberOfTickets, totalPrice);
            return (null, Guid.Empty);                  
        }

        private (Guid, decimal) GetNewTicketCategoryIdByDescription(List<TicketCategory> ticketCategories, TicketDto ticketDto)
        {
            foreach(TicketCategory ticketCategory in ticketCategories)
            {
                if(ticketCategory.Description == ticketDto.Description)
                    return (ticketCategory.Id, ticketCategory.Price * (decimal)ticketDto.NumberOfTickets);
            }
            throw new Exception("No ticket category found for description in update");
        }
        public async Task<Order> Delete(Guid orderId)
        {
            return await _ordersRepository.Delete(orderId);

        }

        public async Task<Order> GetOrder(Guid orderId)
        {
            return await _ordersRepository.GetById(orderId);
        }

        public IEnumerable<Order> GetOrdersByUserId(Guid userId)
        {
            return _ordersRepository.FilterByUserId(userId);
        }


        public async Task<Order> UpdateTicketCategoryAndNumberOfTickets(Guid orderId, TicketDto ticketDto)
        {
            Order orderToUpdate = await _ordersRepository.GetById_EagerTicketCategoriesLoading(orderId);
            TicketCategory currentTicketCategory = orderToUpdate.TicketCategories 
                ?? 
                throw new Exception("No ticket category for given order");

            Task<TicketCategory> task_newTicketCategory = GetTicketCategoryByEventAndDescription(currentTicketCategory.EventId, ticketDto.Description);
            decimal newPrice;
            try
            {
                TicketCategory newTicketCategory = await task_newTicketCategory;
                newPrice = newTicketCategory.Price;

                orderToUpdate.TicketCategoryId = newTicketCategory.Id;
                orderToUpdate.NumberOfTickets = ticketDto.NumberOfTickets;
                orderToUpdate.TotalPrice = (int)newPrice * orderToUpdate.NumberOfTickets;

                await _ordersRepository.Update(orderToUpdate);
                return orderToUpdate;
            }
            catch (Exception e) 
            {
                throw new Exception(e.Message);
            }
        }

        private Task<TicketCategory> GetTicketCategoryByEventAndDescription(Guid? eventId, string description)
        {
            if (eventId == null || string.IsNullOrWhiteSpace(description))
            {
                throw new ArgumentNullException("eventId and description cannot be null or empty.");
            }
            string getApiUrl = $"https://localhost:9090/tms/api/net/ticketcategories?eventId={eventId}&description={description}";
            return Make_TicketCategories_GETApiCall(getApiUrl);
        }

        private async Task<TicketCategory> Make_TicketCategories_GETApiCall(string getApiUrl)
        {
            using (HttpClient httpClient = new HttpClient())
            {
                try
                {
                    HttpResponseMessage response = await httpClient.GetAsync(getApiUrl);
                    response.EnsureSuccessStatusCode();
                    return await response.Content.ReadFromJsonAsync<TicketCategory>() ?? 
                        throw new Exception("Error in reading response");
                } 
                catch (Exception ex)
                {
                    throw new Exception($"Error calling the API: {ex.Message}");
                }
            }
        }
        private async Task<int> GetAvailableTickets(SqlConnection connection, SqlTransaction transaction, List<TicketCategory> ticketCategories, Event @event)
        {
            int boughtTickets = await _ordersRepository.ADO_GetNumberOfTicketsForTicketCategories(connection, transaction, ticketCategories);
            int capacity = await _venuesRepository.ADO_GetCapacityById(connection, transaction, @event?.VenueId ?? Guid.Empty);
            return capacity - boughtTickets;
        }
    }
}
