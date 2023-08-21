import { getRegisterPageTemplate } from "../templates/getRegisterPageTemplate.js";
import { handleRegister } from '../handlers/handleRegister.js';
import { navigateTo } from "../main.js";

export function renderRegisterPage(api_urls) {
    const mainContentDiv = document.querySelector('.main-content-component');
    mainContentDiv.innerHTML = getRegisterPageTemplate();
    createRegisterHandler(api_urls);
    createBackHandler(api_urls);
}

function createRegisterHandler(api_urls){
    const registerButton = document.querySelector(".register-button");
    registerButton.addEventListener("click", () => {
        handleRegister(api_urls);
    });
}

function createBackHandler(api_urls){
    const backText = document.querySelector(".back-link span");
    backText.addEventListener("click", () => {
        let initialUrl = window.location.pathname;
        initialUrl = initialUrl.replace(/\/register$/, '/');
        navigateTo(`${initialUrl}`, api_urls);
    });
}