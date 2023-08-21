export async function fetchOrders(url, userId){
    url = `${url}orders?userId=${userId}`;
    const jwt = sessionStorage.getItem("jwt");
    let response = await fetch(url, {
        method: 'GET',
        headers: {
            "Authorization" : `Bearer ${jwt}`,
        },
    });
    
    if(!response.ok){
        const responseBody = await response.json();
        const message = responseBody.message;
        throw new Error(`${message} 😔`);
    }    
    return response.json();
}