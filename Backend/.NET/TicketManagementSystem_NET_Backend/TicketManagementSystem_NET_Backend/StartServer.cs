using TicketManagementSystem_NET_Backend.Repositories.EventsRepository;
using TicketManagementSystem_NET_Backend.Repositories.NewFolder;
using TicketManagementSystem_NET_Backend.Repositories.OrdersRepository;
using TicketManagementSystem_NET_Backend.Repositories.TicketCategoriesRepository;
using TicketManagementSystem_NET_Backend.Repositories.UsersRepository;
using TicketManagementSystem_NET_Backend.Repositories.VenuesRepository;
using TicketManagementSystem_NET_Backend.Services.OrdersService;
using TicketManagementSystem_NET_Backend.Services.TicketCategoriesService;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(builder =>
    {
        builder.AllowAnyOrigin() // You can restrict origins here if needed
               .AllowAnyMethod()
               .AllowAnyHeader();
    });
});


// Add services to the container.
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// DI
builder.Services.AddTransient<IOrdersRepository, OrdersRepository>();
builder.Services.AddTransient<ITicketCategoriesRepository, TicketCategoriesRepository>();
builder.Services.AddTransient<IEventsRepository, EventsRepository>();
builder.Services.AddTransient<IVenuesRepository, VenuesRepository>();

builder.Services.AddSingleton<IOrdersService, OrdersService>();
builder.Services.AddSingleton<ITicketCategoriesService, TicketCategoriesService>();
builder.Services.AddSingleton<ITicketCategoriesService, TicketCategoriesService>();

builder.Services.AddAutoMapper(AppDomain.CurrentDomain.GetAssemblies());

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseCors();

app.UseHttpsRedirection();


app.UseAuthorization();

app.MapControllers();

app.Run();
