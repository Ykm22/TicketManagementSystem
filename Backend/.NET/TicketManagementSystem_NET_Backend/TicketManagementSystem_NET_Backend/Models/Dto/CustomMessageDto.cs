namespace TicketManagementSystem_NET_Backend.Models.Dto
{
    public class CustomMessageDto
    {
        public string message { get; set; }
        public CustomMessageDto(string message)
        {
            this.message = message;
        }
    }
}
