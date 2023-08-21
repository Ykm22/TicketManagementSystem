import { fetchSaveOrder } from "../network/saveOrder";
export async function handleTicketPurchase(ticketCategoryId, numberOfTickets, eventId, api_urls){
    const userId = sessionStorage.getItem("userId");

    fetchSaveOrder(api_urls.spring, userId, eventId, ticketCategoryId, numberOfTickets)
        .then(data => {
            toastr.success("Order saved! 😯");
            updateEventsDetails(data, eventId);
        })
        .catch(error => {
            toastr.error(error);
        });
}

function updateEventsDetails(order, eventId){
    let boughtTickets = order.numberOfTickets;
    let eventCardToUpdate = document.getElementById(eventId);
    let availableTicketsElement = eventCardToUpdate.querySelector(".availableTickets");
    let currentAvailableTickets = parseInt(availableTicketsElement.textContent.match(/\d+/)[0], 10);
    const updatedAvailableTickets = currentAvailableTickets - boughtTickets;
    availableTicketsElement.textContent = `Tickets left: ${updatedAvailableTickets}`;        
}