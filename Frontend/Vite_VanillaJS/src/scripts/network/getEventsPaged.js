export async function fetchEventsPaged(url, page,  page_size){
    url = `${url}events/paged?page=${page}&size=${page_size}`;
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