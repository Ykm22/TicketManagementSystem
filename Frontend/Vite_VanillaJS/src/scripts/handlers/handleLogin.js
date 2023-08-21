import { navigateTo } from '../main.js';
import { fetchLoginUser } from '../network/loginUser.js'

export function handleLogin(api_urls){
    const emailInput = document.querySelector('.input-field[type="text"]').value;
    const passwordInput = document.querySelector('.input-field[type="password"]').value;
    fetchLoginUser(api_urls.spring, emailInput, passwordInput)
        .then(data => {
            toastr.success("Welcome! 🥳🥂");
            console.log(data);
            const userId = data.userId;
            const jwt = data.jwt;
            const isCustomer = data.customer;

            sessionStorage.setItem("userId", userId);
            sessionStorage.setItem("jwt", jwt);

            let initialUrl = window.location.pathname;
            console.log(isCustomer)
            if(isCustomer){
                navigateTo(`${initialUrl}events`, api_urls);
            } else if(!isCustomer) {// admin
                navigateTo(`${initialUrl}analytics`, api_urls);
            }
        })
        .catch(error => {
            toastr.error(error);
        });
}