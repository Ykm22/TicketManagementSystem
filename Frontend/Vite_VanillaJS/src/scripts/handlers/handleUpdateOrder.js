import { fetchUpdateOrder } from "../network/updateOrder.js";
import { separateDate } from "../utils/getDates.js";

export function handleUpdateOrder(api_urls){
    const modal = document.querySelector(".modal");
    const orderId = modal.getAttribute("orderId");
    const orderCard = document.getElementById(orderId);
    const newDescription = document.getElementById("selectTicketDescriptionInput").value;
    const newNumberOfTickets = sessionStorage.getItem("currentNumberOfTickets");
    
    fetchUpdateOrder(api_urls.net, orderId, newDescription, newNumberOfTickets)
        .then(data => {
            toastr.success("Order updated succesfully! 😲");
           updateOrderCardDetails(data, orderCard);
           updateSessionStorage(data);
           console.log(data);
        })
        .catch(error => {
            toastr.error(error);
        });
    modal.style.display = "none";
}

function updateSessionStorage(order){
    const orders = JSON.parse(sessionStorage.getItem("orders"));
    for(let i = 0; i < orders.length; i++){
        if(orders[i].orderId === order.orderId){
            orders[i].totalPrice = order.totalPrice;
            orders[i].ticketCategoryDto.description = order.description;
            orders[i].numberOfTickets = order.numberOfTickets;
            orders[i].orderedAt = order.orderedAt;
            sessionStorage.setItem("orders", JSON.stringify(orders));
            return;
        }
    }
}

function updateOrderCardDetails(order, orderCard){
    const ticketTypeDiv = orderCard.querySelector(".detail-label");
    ticketTypeDiv.textContent = order.description;
    
    const ticketsAmountDiv = orderCard.querySelector(".tickets-amount");
    ticketsAmountDiv.textContent = "Amount: " + order.numberOfTickets;
    
    const totalPriceDiv = orderCard.querySelector(".total-price");
    totalPriceDiv.textContent = "Total: £" + order.totalPrice;
    
    const [lastUpdateDate, lastUpdateTime] = separateDate(new Date(order.orderedAt));
    const lastUpdateDiv = orderCard.querySelector(".last-update-date");
    lastUpdateDiv.textContent = `${lastUpdateDate} / ${lastUpdateTime}`;

}