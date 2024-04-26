document.addEventListener("DOMContentLoaded", () => {
    if (password.value !== confirmPassword.value) {
        popupMessage.value = 'Passwords do not match.'
        popupFontColor.value = 'red'
        return
    }
    const urlParams = new URLSearchParams(window.location.search)
    const token = urlParams.get('token') ?? ''
})