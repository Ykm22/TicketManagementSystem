using System.Runtime.Serialization;

namespace TicketManagementSystem_NET_Backend.Security
{
    [Serializable]
    internal class NoBearerInAuthorizationException : Exception
    {
        public NoBearerInAuthorizationException()
        {
        }

        public NoBearerInAuthorizationException(string? message) : base(message)
        {
        }

        public NoBearerInAuthorizationException(string? message, Exception? innerException) : base(message, innerException)
        {
        }

        protected NoBearerInAuthorizationException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}