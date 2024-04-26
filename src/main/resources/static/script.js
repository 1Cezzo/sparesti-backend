document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById('forgot-password-modal');
    const modalOverlay = document.querySelector('.modal-overlay');

    hideForgotPasswordModal(); // Ensure the modal is hidden initially

    modalOverlay.addEventListener('click', function(event) {
        if (event.target === modalOverlay) { // Check if the click is on the overlay
            closeForgotPasswordModal();
        }
    });
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
        || localStorage.getItem('darkMode') === 'true') {
        document.body.classList.add('dark');
    }
    updateImageSource();
    document.getElementById('forgot-password').addEventListener('click', showForgotPasswordModal);

    const toggleButton = document.getElementById('toggle-dark-mode');
    const signUp = document.getElementById('signup-link');
    const login = document.getElementById('login-link');
    const signupForm = document.getElementById('signup-form');

    toggleButton.addEventListener('click', function() {
        if(document.body.classList.toggle('dark')) {
            localStorage.setItem('darkMode', 'true');
        } else {
            localStorage.setItem('darkMode', 'false');
        }
        updateImageSource();
    });

    signUp.addEventListener('click', toggleForm);
    login.addEventListener('click', toggleForm);
    signupForm.addEventListener('submit', submitSignUp);
});

function updateImageSource() {
    const imageElement = document.getElementById('piggy-bank-image');
    const darkMode = localStorage.getItem('darkMode');
    if (darkMode === 'true') {
        imageElement.src = './images/long-logo-darkmode.png';
    } else {
        imageElement.src = './images/long-logo.png';
    }
}

function toggleForm() {
    document.getElementById('signup-container').classList.toggle('hidden');
    document.getElementById('login-container').classList.toggle('hidden');
}

function hideForgotPasswordModal() {
    document.getElementById('forgot-password-modal').classList.add('hidden');
}

function closeForgotPasswordModal() {
    document.getElementById('forgot-password-modal').classList.add('hidden');
}


function showForgotPasswordModal() {
    document.getElementById('forgot-password-modal').classList.remove('hidden');
}

async function sendResetLink() {
    const email = document.getElementById('reset-email').value;
    if (!email) {
        alert('Please enter an email address.');
        return;
    }

    const url = `/api/sendEmail?to=${encodeURIComponent(email)}`; // Append email as a query parameter

    try {
        console.log('Sending email to:', email); // Debug: log the email being sent
        const response = await fetch(url, {
            method: 'POST', // Ensure method is POST
            headers: {
                'Accept': '*/*' // Match header as per your working example
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
        closeForgotPasswordModal(); // Ensure this function exists and is accessible
    }
}




document.addEventListener("DOMContentLoaded", () => {
    hideForgotPasswordModal(); // Hide the modal initially
    // Rest of your code...
    document.getElementById('forgot-password').addEventListener('click', showForgotPasswordModal);
});

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
        alert("Success!")
        toggleForm();
    } else {
        console.error('Failed to create user');
    }
    return false;
}

