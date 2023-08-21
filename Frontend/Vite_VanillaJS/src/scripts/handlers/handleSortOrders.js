export function handleSortOrders(orders){
    const selectedCriteria = document.querySelector('input[name="sortCriteria"]:checked').value;
    const selectedOrder = document.querySelector('input[name="sortOrder"]:checked').value;
    orders = sortOrdersContainer(orders, selectedCriteria, selectedOrder);
    return orders;
}

function sortOrdersContainer(orders, selectedCriteria, selectedOrder){
    if (selectedCriteria === "name") {
        if (selectedOrder === "ascending") {
            orders.sort((a, b) => a.eventDto.name.localeCompare(b.eventDto.name));
        } else if (selectedOrder === "descending") {
            orders.sort((a, b) => b.eventDto.name.localeCompare(a.eventDto.name));
        }
    } else if (selectedCriteria === "price") {
        if (selectedOrder === "ascending") {
            orders.sort((a, b) => a.totalPrice - b.totalPrice);
        } else if (selectedOrder === "descending") {
            orders.sort((a, b) => b.totalPrice - a.totalPrice);
        }
    }
    sessionStorage.setItem("orders", JSON.stringify(orders));
    return orders;
}