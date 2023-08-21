using System.Runtime.Serialization;

namespace TicketManagementSystem_NET_Backend.Security
{
    [Serializable]
    internal class NoAuthorizationException : Exception
    {
        public NoAuthorizationException()
        {
        }

        public NoAuthorizationException(string? message) : base(message)
        {
        }

        public NoAuthorizationException(string? message, Exception? innerException) : base(message, innerException)
        {
        }

        protected NoAuthorizationException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}