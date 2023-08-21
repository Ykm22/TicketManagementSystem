export async function fetchEventTypes(url){
    url = `${url}event_types`
    const jwt = sessionStorage.getItem("jwt");
    
    const response = await fetch(url, {
        mode: 'cors',
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${jwt}`,
        },
    });
    if(!response.ok){
        throw new Error("Error fetching event types 😓");
    }
    return response.json();
}