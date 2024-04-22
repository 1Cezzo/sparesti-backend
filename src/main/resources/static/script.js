document.addEventListener("DOMContentLoaded", () => {
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
        || localStorage.getItem('darkMode') === 'true') {
        document.body.classList.add('dark');
    }
    updateImageSource();
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


