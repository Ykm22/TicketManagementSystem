
export async function fetchEventYears(url){
    url = `${url}event_years`;
    let response = await fetch(url, {
        method: 'GET',
    })
    if(!response.ok){
        throw new Error("error");
    }
    return response.json();
}