function matchPasswords() {

    const currentLocale = document.validateForm.currentLocale.value;
    const password = document.validateForm.newPassword.value;
    const passwordRepeat = document.validateForm.repeatNewPassword.value;
    let returnMessage;
    if (currentLocale === "uk") {
        returnMessage = "Паролі не співпадають!";
    } else {
        returnMessage = "Passwords don`t match";
    }
    if (password === passwordRepeat) {
        return true
    } else {
        document.getElementById("passwordLock").innerHTML = returnMessage;
        return false;
    }
}