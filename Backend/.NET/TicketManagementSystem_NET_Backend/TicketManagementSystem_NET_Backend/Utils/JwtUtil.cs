using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using TicketManagementSystem_NET_Backend.Models;

namespace TicketManagementSystem_NET_Backend.Utils
{
    public class JwtUtil
    {
        private readonly IConfiguration _configuration;
        private readonly static string SECRET_KEY = "0123456789abcdef0123456789abcdef0123456789abcdef";

        public JwtUtil(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public static string ExtractEmail(string token)
        {
            var handler = new JwtSecurityTokenHandler();
            var validationParameters = new TokenValidationParameters
            {
                ValidateIssuerSigningKey = true,
                IssuerSigningKey = new SymmetricSecurityKey(Convert.FromBase64String(SECRET_KEY)),
                ValidateIssuer = false,
                ValidateAudience = false,
                ValidAlgorithms = new[] { SecurityAlgorithms.HmacSha256 } // Specify HS256 algorithm
            };

            SecurityToken securityToken;
            var claimsPrincipal = handler.ValidateToken(token, validationParameters, out securityToken);
            JwtSecurityToken jwtToken = securityToken as JwtSecurityToken;

            var subjectClaim = jwtToken.Claims.FirstOrDefault(claim => claim.Type == JwtRegisteredClaimNames.Sub)?.Value;

            return subjectClaim;

        }

        //private static bool IsTokenExpired(string token)
        //{
        //    var expiration = ExtractExpiration(token);
        //    return expiration < DateTime.UtcNow;
        //}

        //public static DateTime ExtractExpiration(string token)
        //{
        //    return ExtractClaim(token, claims => claims.Expiration.ToUniversalTime());
        //}
    }
}
