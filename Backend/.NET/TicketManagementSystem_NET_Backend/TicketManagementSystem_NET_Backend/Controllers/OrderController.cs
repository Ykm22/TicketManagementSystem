using AutoMapper;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Reflection.Metadata.Ecma335;
using System.Text.Json;
using System.Text.Json.Serialization;
using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Models.Dto;
using TicketManagementSystem_NET_Backend.Security;
using TicketManagementSystem_NET_Backend.Services.OrdersService;

namespace TicketManagementSystem_NET_Backend.Controllers
{
    [ApiController]
    [EnableCors]
    [Route("tms/api/net/orders")]
    public class OrderController : Controller
    {
        private readonly IOrdersService _ordersService;
        private readonly IMapper _mapper;

        public OrderController(IOrdersService ordersService, IMapper mapper)
        {
            _ordersService = ordersService;
            _mapper = mapper;
        }

        [HttpGet]
        public ActionResult<IEnumerable<OrderDto>> GetOrdersByUserId([FromQuery] Guid userId)
        {
            // test
            return Ok(_ordersService.GetOrdersByUserId(userId)
                .Select(o => GetDto(o))
                .ToList());
        }

        [HttpPatch("{orderId}")]
        public async Task<ActionResult<OrderDto>> UpdateTicketCategoryAndNumberOfTickets(
            [FromRoute] Guid orderId,
            [FromBody] TicketDto ticketDto)
        {
            try
            {
                MyCustomSecurity.ValidateRequest(HttpContext, UserRoles.CUSTOMER);
                (Order updatedOrder, Guid eventId) = await _ordersService.ADO_Update(orderId, ticketDto);

                OrderDto orderDto = GetDto(updatedOrder);
                orderDto.Description = ticketDto.Description;
                orderDto.EventId = eventId;
                return orderDto;
            }
            catch (NoAuthorizationException)
            {
                return Unauthorized();
            }
            catch (NoBearerInAuthorizationException)
            {
                return Unauthorized();
            }
            catch (ResourceForbiddenException)
            {
                return Forbid();
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }
        [HttpPost]
        public async Task<ActionResult<OrderDto>> SaveOrder(
            [FromBody] OrderDtoPost orderPostDto,
            [FromQuery] Guid customerId
        )
        {
            try
            {
<<<<<<< HEAD
                MyCustomSecurity.ValidateRequest(HttpContext, UserRoles.CUSTOMER);
                (Order savedOrder, string description) = await _ordersService.ADO_Save(orderPostDto, customerId);
=======
                //Order updatedOrder = await _ordersService.UpdateTicketCategoryAndNumberOfTickets(orderId, ticketDto);
                //(Order savedOrder, string description) = await _ordersService.ADO_Save(orderPostDto, customerId);
                (Order savedOrder, string description) = await _ordersService.ADO_Save_SQL(orderPostDto, customerId);
>>>>>>> main

                OrderDto orderDto = GetDto(savedOrder);
                orderDto.Description = description;
                return orderDto;
            }
            catch (NoAuthorizationException)
            {
                return Unauthorized();
            }
            catch (NoBearerInAuthorizationException)
            {
                return Unauthorized();
            }
            catch (ResourceForbiddenException)
            {
                return Forbid();
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpDelete("{orderId}")]
        public async Task<ActionResult<Order>> DeleteOrder(
            [FromRoute] Guid orderId)
        {
            try
            {
                MyCustomSecurity.ValidateRequest(HttpContext, UserRoles.CUSTOMER);

                Order orderToDelete = await _ordersService.Delete(orderId);
                return Ok(new CustomMessageDto(orderId.ToString()));
            }
            catch (NoAuthorizationException)
            {
                return Unauthorized();
            }
            catch (NoBearerInAuthorizationException)
            {
                return Unauthorized();
            }
            catch (ResourceForbiddenException)
            {
                return Forbid();
            }
            catch (Exception ex)
            {
                return NotFound(new CustomMessageDto(ex.Message));
            }
        }

        private OrderDto GetDto(Order order)
        {
            OrderDto orderDto = _mapper.Map<OrderDto>(order);
            orderDto.EventId = order.TicketCategories?.EventId ?? Guid.NewGuid();
            orderDto.Description = order.TicketCategories?.Description ?? String.Empty;
            return orderDto;
        }

    }
}