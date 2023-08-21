using System;
using System.Collections.Generic;

namespace TicketManagementSystem_NET_Backend.Models;

public partial class Venue
{
    public Guid Id { get; set; }

    public string? Location { get; set; }

    public string Type { get; set; } = null!;

    public int? Capacity { get; set; }

    public decimal? PricePerHour { get; set; }

    public virtual ICollection<Event> Events { get; set; } = new List<Event>();
}
