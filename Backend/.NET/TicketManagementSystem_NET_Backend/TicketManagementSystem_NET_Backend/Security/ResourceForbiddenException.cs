using System.Runtime.Serialization;

namespace TicketManagementSystem_NET_Backend.Security
{
    [Serializable]
    internal class ResourceForbiddenException : Exception
    {
        public ResourceForbiddenException()
        {
        }

        public ResourceForbiddenException(string? message) : base(message)
        {
        }

        public ResourceForbiddenException(string? message, Exception? innerException) : base(message, innerException)
        {
        }

        protected ResourceForbiddenException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}