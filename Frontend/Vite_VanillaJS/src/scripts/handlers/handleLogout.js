import { navigateTo } from "../main";

export function handleLogout(api_urls){
    sessionStorage.setItem("jwt", "");
    navigateTo('/', api_urls);
}