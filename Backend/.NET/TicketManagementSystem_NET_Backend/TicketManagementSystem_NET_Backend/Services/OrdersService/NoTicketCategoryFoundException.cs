using System.Runtime.Serialization;

namespace TicketManagementSystem_NET_Backend.Services.OrdersService
{
    [Serializable]
    internal class NoTicketCategoryFoundException : Exception
    {
        public NoTicketCategoryFoundException()
        {
        }

        public NoTicketCategoryFoundException(string? message) : base(message)
        {
        }

        public NoTicketCategoryFoundException(string? message, Exception? innerException) : base(message, innerException)
        {
        }

        protected NoTicketCategoryFoundException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}