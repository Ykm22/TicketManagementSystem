export async function fetchSaveOrder(url, userId, eventId, ticketCategoryId, numberOfTickets){
    url = `${url}orders?customerId=${userId}`;
    const jwt = sessionStorage.getItem("jwt");
    const requestBody = {
        eventId: eventId,
        ticketCategoryId: ticketCategoryId,
        numberOfTickets: numberOfTickets,
    };
    const response = await fetch(url, {
        mode: 'cors',
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${jwt}`,
        },
        body: JSON.stringify(requestBody)
    });
    if(!response.ok){
        throw new Error("Error saving order 😓");
    }
    return response.json();
}