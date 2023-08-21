using System.Runtime.Serialization;

namespace TicketManagementSystem_NET_Backend.Services.OrdersService
{
    [Serializable]
    internal class IllegalTicketsAmountException : Exception
    {
        public IllegalTicketsAmountException()
        {
        }

        public IllegalTicketsAmountException(string? message) : base(message)
        {
        }

        public IllegalTicketsAmountException(string? message, Exception? innerException) : base(message, innerException)
        {
        }

        protected IllegalTicketsAmountException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}