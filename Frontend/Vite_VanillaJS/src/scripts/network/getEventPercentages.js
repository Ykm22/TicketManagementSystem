export async function fetchEventPercentages(url, eventType){
    url = `${url}actual_age_percentages?event_type=${eventType}`;
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
        throw new Error("Error fetching event type age percentages 😓");
    }
    return response.json();
}