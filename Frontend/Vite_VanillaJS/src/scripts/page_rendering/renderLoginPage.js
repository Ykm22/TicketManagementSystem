import { getLoginPageTemplate } from '../templates/getLoginPageTemplate.js' 
import { createLoader, deleteLoader } from './loader.js';
import { handleLogin } from '../handlers/handleLogin.js';
import { navigateTo } from '../main.js';

export function renderLoginPage(api_urls){
    deleteLoader();
    sessionStorage.setItem("jwt", "");
    const mainContentDiv = document.querySelector('.main-content-component');
    mainContentDiv.innerHTML = getLoginPageTemplate();
    createLoginHandler(api_urls);
    createRegisterHandler(api_urls);
}

function createLoginHandler(api_urls){
    const loginButton = document.querySelector('.login-button');
    loginButton.addEventListener("click", () => {
        handleLogin(api_urls);
    });
}

function createRegisterHandler(api_urls){
    const registerText = document.querySelector('.register-link span');
    registerText.addEventListener("click", () => {
        let initialUrl = window.location.pathname;
        console.log(initialUrl);
        initialUrl = initialUrl.replace(/\/login$/, '/');
        console.log(initialUrl);
        navigateTo(`${initialUrl}register`, api_urls);
    });
}