export async function fetchFilterEvents(url, location, eventType){
    url = `${url}events?location=${location}&eventType=${eventType}`;
    const jwt = sessionStorage.getItem("jwt");
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            "Authorization" : `Bearer ${jwt}`,
        },
    });
    if(!response.ok){
        throw new Error("Error filtering events 😟");
    }
    return response.json();
}