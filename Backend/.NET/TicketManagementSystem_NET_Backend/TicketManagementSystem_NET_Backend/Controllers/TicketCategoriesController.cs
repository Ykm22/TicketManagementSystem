using AutoMapper;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Services.TicketCategoriesService;

namespace TicketManagementSystem_NET_Backend.Controllers
{
    [ApiController]
    [EnableCors]
    [Route("tms/api/net/ticketcategories")]
    public class TicketCategoriesController : Controller
    {
        private readonly ITicketCategoriesService _ticketCategoriesService;

        public TicketCategoriesController(ITicketCategoriesService ticketCategoriesService)
        {
            _ticketCategoriesService = ticketCategoriesService;
        }
        [HttpGet]
        public async Task<ActionResult<TicketCategory>> GetTicketCategory_ByEventIdAndDescription(
            [FromQuery] Guid eventId,
            [FromQuery] string description)
        {
            try
            {
                TicketCategory ticketCategory = await _ticketCategoriesService.GetTicketCategory_ByEventIdAndDescription(eventId, description);
                return Ok(ticketCategory);
            }
            catch (Exception ex)
            {
                return NotFound();
            }
        }
    }
}
