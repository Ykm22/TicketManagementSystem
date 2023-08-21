export function getLoginPageTemplate(){
    return `
        <div class="login-container">
            <div class="centered-box">
                <div class="background-image"></div> <!-- New div for background image -->
                <div class="login-content">
                    <div class="text-title">
                        Ticket Management System
                    </div>
                    <div class="login inputs">
                        <input type="text" class="input-field" placeholder="Email">
                        <input type="password" class="input-field" placeholder="Password">
                    </div>
                    <button class="login-button">Login</button>
                        <div class="separator"></div>
                        <div class="register-link">
                            Don't have an account? <span>Register</span>
                        </div>
                    </div>
            </div>
        </div>
    `;
}