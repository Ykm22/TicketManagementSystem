export async function fetchEvents(url){
    url = `${url}events`;
    const jwt = sessionStorage.getItem("jwt");
    let response = await fetch(url, {
        method: 'GET',
        headers: {
            "Authorization" : `Bearer ${jwt}`,
        },
    });
    if(!response.ok){
        throw new Error("Bad network response 😔");
    }
    return response.json();
}