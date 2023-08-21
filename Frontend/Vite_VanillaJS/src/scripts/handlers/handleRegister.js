import { navigateTo } from '../main.js';
import { fetchRegisterUser } from '../network/registerUser.js'

export function handleRegister(api_urls){
    const emailInput = document.querySelector(".input-field-email").value;
    const passwordInput = document.querySelector(".input-field-password").value;
    const reenterPasswordInput = document.querySelector(".input-field-retype-password").value;
    const age = document.querySelector(".input-field-age").value;
    const sex = document.querySelector(".select-sex").value;

    if(passwordInput !== reenterPasswordInput){
        toastr.error("Passwords don't match!");
        return;
    }

    fetchRegisterUser(api_urls.spring, emailInput, passwordInput, age, sex)
        .then(data => {
            toastr.success("Successfully registered! 🏄‍♀️")
            let initialUrl = window.location.pathname;
            initialUrl = initialUrl.replace(/\/register$/, '/');
            navigateTo(initialUrl, api_urls);
        })
        .catch(error => {
            toastr.error(error);
        });
}