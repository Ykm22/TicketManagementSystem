import { deleteLoader } from "../page_rendering/loader";
export function getRegisterPageTemplate(){
    deleteLoader();
    return `
        <div class="register-container">
            <div class="centered-box">
                <div class="background-image"></div> <!-- New div for background image -->
                <div class="register-content">
                    <div class="text-title">
                        Ticket Management System
                    </div>
                    <div class="register inputs">
                        <input type="text" class="input-field-email" placeholder="Email">
                        <input type="password" class="input-field-password" placeholder="Password">
                        <input type="password" class="input-field-retype-password" placeholder="Re-enter password">
                        <input type="number" class="input-field-age" placeholder="Age">
                        <select class="select-sex">
                            <option disabled selected>Sex</option>
                            <option value="M">M</option>
                            <option value="F">F</option>
                        </select>
                    </div>
                    <button class="register-button">Register</button>
                    <div class="separator"></div>
                    <div class="back-link">
                        <span>Back</span>
                    </div>
                </div>
            </div>

        </div>
    `;
}