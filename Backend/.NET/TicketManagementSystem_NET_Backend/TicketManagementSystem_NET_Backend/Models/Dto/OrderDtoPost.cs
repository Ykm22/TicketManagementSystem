namespace TicketManagementSystem_NET_Backend.Models.Dto
{
    public class OrderDtoPost
    {
        public Guid EventId { get; set; }
        public Guid TicketCategoryId { get; set; }
        public int NumberOfTickets { get; set; }

    }
}
