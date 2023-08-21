export async function fetchPredictedWinningsPlot(url){
    url = `${url}predict_winnings`;
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
        throw new Error("Error saving order 😓");
    }
    const imageBlob = await response.blob();
    return URL.createObjectURL(imageBlob);
}