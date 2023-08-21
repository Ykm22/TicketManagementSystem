using System;
using System.Collections.Generic;

namespace TicketManagementSystem_NET_Backend.Models;

public partial class Order
{
    public Guid OrderId { get; set; }

    public Guid? UserId { get; set; }

    public Guid? TicketCategoryId { get; set; }

    public DateTime OrderedAt { get; set; }

    public int NumberOfTickets { get; set; }

    public int? TotalPrice { get; set; }

    public virtual TicketCategory? TicketCategories { get; set; }

    public virtual User? User { get; set; }
}
