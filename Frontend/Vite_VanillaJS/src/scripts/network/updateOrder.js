export async function fetchUpdateOrder(url, orderId, description, numberOfTickets){
    url = `${url}orders/${orderId}`;
    const jwt = sessionStorage.getItem("jwt");
    const requestBody = {
        description: description,
        numberOfTickets: numberOfTickets,
    };
    let response = await fetch(url, {
        method: 'PATCH',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${jwt}`,
        },
        body: JSON.stringify(requestBody),
    });
    if(!response.ok){
        throw new Error("Error updating order 😔");
    }
    return response.json();
}