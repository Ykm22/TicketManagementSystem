using System;
using System.Collections.Generic;

namespace TicketManagementSystem_NET_Backend.Models;

public partial class User
{
    public Guid Id { get; set; }

    public string Email { get; set; } = null!;

    public string PasswordHash { get; set; } = null!;

    public string Salt { get; set; } = null!;

    public string? Sex { get; set; }

    public short? Age { get; set; }

    public bool IsCustomer { get; set; }

    public virtual ICollection<Order> Orders { get; set; } = new List<Order>();
}
