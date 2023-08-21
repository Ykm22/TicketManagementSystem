using System;
using System.Collections.Generic;

namespace TicketManagementSystem_NET_Backend.Models;

public partial class TicketCategory
{
    public Guid Id { get; set; }

    public Guid? EventId { get; set; }

    public string Description { get; set; } = null!;

    public decimal Price { get; set; }

    public virtual Event? Event { get; set; }

    public virtual ICollection<Order> Orders { get; set; } = new List<Order>();

    public static implicit operator TicketCategory(Task<TicketCategory> v)
    {
        throw new NotImplementedException();
    }
}
