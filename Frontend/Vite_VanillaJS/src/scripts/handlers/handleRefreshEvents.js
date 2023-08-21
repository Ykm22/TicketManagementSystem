import { renderContent } from "../page_rendering/renderContent";

export function handleRefreshEvents(api_urls){
    let initialUrl = window.location.pathname;
    renderContent(`${initialUrl}`, api_urls);
}