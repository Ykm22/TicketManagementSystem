namespace TicketManagementSystem_NET_Backend.Models.Dto
{
    public class TicketDto
    {
        public string Description { get; set; }
        public int NumberOfTickets { get; set; }

        public TicketDto(string description, int numberOfTickets)
        {
            Description = description;
            NumberOfTickets = numberOfTickets;
        }
    }
}
