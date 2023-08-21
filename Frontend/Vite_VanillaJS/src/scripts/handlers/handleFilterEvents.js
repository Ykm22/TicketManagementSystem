import { fetchFilterEvents } from "../network/filterEvents.js";

export async function handleFilterEvents(api_urls, populateEventsContainer){
    const location = document.getElementById("selectLocationFilterInput").value;
    const eventType = document.getElementById("selectEventTypeFilterInput").value;

    try {
        validateFilterInputs(location, eventType);
    } catch (error) {
        toastr.error(error);
        return;
    }
    fetchFilterEvents(api_urls.spring, location, eventType)
        .then(data => {
            // toastr.success("Events loaded successfully! 😉");
            populateEventsContainer(data, api_urls);
            return data;
        })
        .catch(error => {
            toastr.error(error);
        });
}

function validateFilterInputs(location, eventType){
    if(location == "" || eventType == ""){
        throw new Error("Please enter correct inputs 🥲");
    }
}