using AutoMapper;
using TicketManagementSystem_NET_Backend.Models;
using TicketManagementSystem_NET_Backend.Models.Dto;

namespace TicketManagementSystem_NET_Backend.Profiles
{
    public class OrderProfile : Profile
    {
        public OrderProfile()
        {
            CreateMap<Order, OrderDto>().ReverseMap();
        }
    }
}
