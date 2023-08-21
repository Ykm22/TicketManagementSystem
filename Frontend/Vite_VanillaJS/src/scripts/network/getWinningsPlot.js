export async function fetchWinningsPlot(url, years){
    url = `${url}draw_graph_years`;
    const requestBody = {
        years: years,
    };
    const jwt = sessionStorage.getItem("jwt");
    
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
        throw new Error("Error fetching winning plot 😓");
    }
    const imageBlob = await response.blob();
    return URL.createObjectURL(imageBlob);
}