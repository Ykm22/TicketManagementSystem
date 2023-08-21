export async function fetchDeleteOrder(url, orderId){
    url = `${url}orders/${orderId}`;
    const jwt = sessionStorage.getItem("jwt");
    let response = await fetch(url, {
        method: 'DELETE',
        mode: 'cors',
        headers: {
            "Authorization" : `Bearer ${jwt}`,
        },
    });
    if(!response.ok){
        throw new Error("Bad network response ☹️");
    }
    console.log(response);
    return response.json();
}