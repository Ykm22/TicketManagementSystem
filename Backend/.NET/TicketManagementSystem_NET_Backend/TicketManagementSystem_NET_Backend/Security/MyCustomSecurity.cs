using TicketManagementSystem_NET_Backend.Repositories.UsersRepository;
using TicketManagementSystem_NET_Backend.Utils;

namespace TicketManagementSystem_NET_Backend.Security
{
    public enum UserRoles
    {
        CUSTOMER,
        ADMIN
    };
    public class MyCustomSecurity
    {
        private static IUsersRepository _usersRepository;

        public MyCustomSecurity()
        {
        }

        public static async void ValidateRequest(HttpContext context, UserRoles userRole)
        {
            _usersRepository = new UsersRepository();
            if (!RequestContainsAuthorization(context.Request))
            {
                throw new NoAuthorizationException("No authorization header in request!");
            }

            string jwt = ExtractJWT(context.Request);
            string email = JwtUtil.ExtractEmail(jwt);
            bool isCustomer = await GetUserRole(email);

            if (isCustomer && userRole != UserRoles.CUSTOMER)
            {
                throw new ResourceForbiddenException("Resource not allowed!");
            }

            if (!isCustomer && userRole != UserRoles.ADMIN)
            {
                throw new ResourceForbiddenException("Resource not allowed!");
            }
        }

        public static bool RequestContainsAuthorization(HttpRequest request)
        {
            return !string.IsNullOrEmpty(request.Headers["Authorization"]);
        }

        public static string ExtractJWT(HttpRequest request)
        {
            string authorizationHeader = request.Headers["Authorization"];

            if (!authorizationHeader.StartsWith("Bearer"))
            {
                throw new NoBearerInAuthorizationException("Authorization header not starting with Bearer!");
            }

            return authorizationHeader.Substring(7);
        }

        public async static Task<bool> GetUserRole(string email)
        {
            return await _usersRepository.FindRoleByEmail(email);
        }
    }
}