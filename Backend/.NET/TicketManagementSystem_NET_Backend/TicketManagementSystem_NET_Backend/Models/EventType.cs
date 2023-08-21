using System;
using System.Collections.Generic;

namespace TicketManagementSystem_NET_Backend.Models;

public partial class EventType
{
    public Guid Id { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<Event> Events { get; set; } = new List<Event>();
}
