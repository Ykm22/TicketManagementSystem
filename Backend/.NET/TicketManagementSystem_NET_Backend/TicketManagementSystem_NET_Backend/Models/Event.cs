using System;
using System.Collections.Generic;

namespace TicketManagementSystem_NET_Backend.Models;

public partial class Event
{
    public Guid Id { get; set; }

    public Guid? VenueId { get; set; }

    public Guid? EventTypeId { get; set; }

    public string Description { get; set; } = null!;

    public string Name { get; set; } = null!;

    public DateTime StartDate { get; set; }

    public DateTime EndDate { get; set; }

    public virtual EventType? EventType { get; set; }

    public virtual ICollection<TicketCategory> TicketCategories { get; set; } = new List<TicketCategory>();

    public virtual Venue? Venue { get; set; }
}
