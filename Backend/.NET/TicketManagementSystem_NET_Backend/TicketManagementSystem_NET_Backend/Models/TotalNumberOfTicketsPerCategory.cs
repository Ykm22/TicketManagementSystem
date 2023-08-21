using System;
using System.Collections.Generic;

namespace TicketManagementSystem_NET_Backend.Models;

public partial class TotalNumberOfTicketsPerCategory
{
    public Guid TicketCategoryId { get; set; }

    public int? NumberOfTickets { get; set; }

    public int? TotalPrice { get; set; }
}
