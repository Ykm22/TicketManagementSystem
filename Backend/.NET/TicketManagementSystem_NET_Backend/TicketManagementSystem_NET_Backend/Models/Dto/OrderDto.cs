namespace TicketManagementSystem_NET_Backend.Models.Dto
{
    public class OrderDto
    {
        public Guid OrderId { get; set; }
        public Guid EventId { get; set; }
        public DateTime OrderedAt { get; set; }
        public Guid TicketCategoryId { get; set; }
        public int NumberOfTickets { get; set; }
        public int TotalPrice { get; set; }
        public string Description { get; set; }
        public OrderDto() { }
    }
}
