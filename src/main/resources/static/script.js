// Handles all DOMContentLoaded activities
document.addEventListener("DOMContentLoaded", () => {
    setupDarkModeToggle();
    updateImageSource();
    setupFormSubmissions();
    setupModals();
});

// Toggle visibility between login and signup forms
function toggleForm() {
    const signupContainer = document.getElementById('signup-container');
    const loginContainer = document.getElementById('login-container');

    // Check if the elements exist before attempting to toggle
    if (signupContainer && loginContainer) {
        signupContainer.classList.toggle('hidden');
        loginContainer.classList.toggle('hidden');
    } else {
        console.error('One or both containers not found');
    }
}

// Attach event listeners to toggle form visibility
document.addEventListener("DOMContentLoaded", () => {
    const signUpLink = document.getElementById('signup-link');
    const loginLink = document.getElementById('login-link');

    // Listen for click events on signup and login links
    signUpLink?.addEventListener('click', toggleForm);
    loginLink?.addEventListener('click', toggleForm);
});


// Toggle Dark Mode and update image source based on the setting
function setupDarkModeToggle() {
    const toggleButton = document.getElementById('toggle-dark-mode');
    if (toggleButton) {
        toggleButton.addEventListener('click', () => {
            const isDarkMode = document.body.classList.toggle('dark');
            localStorage.setItem('darkMode', isDarkMode ? 'true' : 'false');
            updateImageSource();
        });
    }
}

// Update image sources based on dark mode
function updateImageSource() {
    const imageElements = document.querySelectorAll('.piggy-bank-image img');
    const darkMode = localStorage.getItem('darkMode') === 'true';
    imageElements.forEach(img => {
        img.src = darkMode ? './images/long-logo-darkmode.png' : './images/long-logo.png';
    });
}

// Setup form submissions and their specific behaviors
function setupFormSubmissions() {
    const signupForm = document.getElementById('signup-form');

    signupForm?.addEventListener('submit', event => submitSignUp(event));
}

// Handle showing and hiding modals
function setupModals() {
    const forgotPasswordButton = document.getElementById('forgot-password');
    const closeModalButtons = document.querySelectorAll('.cancel-btn');

    forgotPasswordButton?.addEventListener('click', showForgotPasswordModal);
    closeModalButtons.forEach(button => {
        button.addEventListener('click', () => closeForgotPasswordModal());
    });
}

// Display and hide forgot password modal
function showForgotPasswordModal() {
    const modal = document.getElementById('forgot-password-modal');
    modal?.classList.remove('hidden');
}

function closeForgotPasswordModal() {
    const modal = document.getElementById('forgot-password-modal');
    modal?.classList.add('hidden');
}

async function submitSignUp(event) {
    event.preventDefault();
    const res = await fetch('/api/users/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: document.getElementById('email').value,
            password: document.getElementById('signup-password').value
        })
    })
    if (res.ok) {
        toggleForm();
    } else {
        // Attempt to read the JSON response to get more detailed error messages
        try {
            const data = await res.json(); // Assuming the server sends a JSON response with an error message field
            const errorMessage = data.message || 'Denne eposten er allerede ';
            displayErrorMessage('signup-container', errorMessage); // Display a specific error message if available
        } catch (error) {
            // If parsing the JSON fails or no message is included, display a generic error
            displayErrorMessage('signup-container', 'An error occurred during signup. Please try again.');
        }
    }
    return false; // Prevent the default form submission
}

async function sendResetLink() {
    const email = document.getElementById('reset-email').value;
    if (!email) {
        alert('Please enter an email address.');
        return;
    }

    const url = `/api/sendEmail?to=${encodeURIComponent(email)}`;

    try {
        console.log('Sending email to:', email); // Debug: log the email being sent
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Accept': '*/*'
            }
        });

        if (response.ok) {
            const responseData = await response.json();
            console.log('Response Data:', responseData); // Debug: log response data
            alert(`A password reset link has been sent to ${email}`);
        } else {
            const error = await response.text();
            console.error('Error response text:', error); // Debug: log error text
            throw new Error(`Failed to send email: ${error}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to send password reset link. Please try again.');
    } finally {
        closeForgotPasswordModal();
    }
}

async function resetPassword() {
    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const email = document.getElementById('email').value;
    const token = new URLSearchParams(window.location.search).get('token');

    if (!email) {
        displayErrorMessage('reset-error-message', 'Vennligst skriv inn epost.');
        return;
    }
    if (!token) {
        displayErrorMessage('reset-error-message', 'Ikke mulig å nullstille password, prøv ny link.');
        return;
    }
    if (newPassword !== confirmPassword) {
        displayErrorMessage('reset-error-message', 'Passordene samstiller ikke.');
        return;
    }
    const payload = {
        username: email,
        password: newPassword,
        token: token
    };

    try {
        const response = await fetch('/api/password-reset/reset-password', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert('Your password has been reset successfully.');
            window.location.href = '/login.html';
        } else {
            const errorText = await response.text();
            console.error('Failed to reset password:', errorText);
            throw new Error(errorText || 'Feil, greide ikke å nullstille passord.');
        }
    } catch (error) {
        console.error('Fetch Error:', error);
        alert(error.message || 'Failed to reset password. Please try again.');
    }
}

function displayErrorMessage(containerId, message) {
    const container = document.getElementById(containerId);
    const errorMessageDiv = container.querySelector('.error-message');
    if(errorMessageDiv) {
        errorMessageDiv.textContent = message;
    } else {
        console.error('Error message div not found in the container');
    }
}