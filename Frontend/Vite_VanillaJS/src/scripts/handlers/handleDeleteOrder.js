import { fetchDeleteOrder } from "../network/deleteOrder";

export function handleDeleteOrder(api_urls){
    const modal = document.querySelector(".modal");
    const orderId = modal.getAttribute("orderId");
    const orderCard = document.getElementById(orderId);

    fetchDeleteOrder(api_urls.net, orderId)
        .then(data => {
            if(data.message === orderId){
                toastr.success("Order deleted successfully! 🫰");
                const ordersContainer = document.querySelector(".orders");
                ordersContainer.removeChild(orderCard);
                const orders = JSON.parse(sessionStorage.getItem("orders"));
                for(let i = 0; i < orders.length; i++){
                    if(orders[i].orderId === orderId){
                        orders.splice(i, 1);
                        sessionStorage.setItem("orders", JSON.stringify(orders));
                    }
                }
            }
        })
        .catch(error => {
            toastr.error(error);
        });
    modal.style.display = "none";
}