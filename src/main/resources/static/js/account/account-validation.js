function loginButtonClicked() {
    var login = document.getElementById("inputlogin").value.trim();
    var login_validation = document.getElementById("login-validation");
    if (login.length < 4 || login.length > 32) {
        login_validation.classList.add('d-block');
    } else {
        login_validation.classList.remove('d-block');
        window.location.replace(window.location.href + "/zmiana-loginu&login=" + login);
    }
}

function passwordButtonClicked() {
    var password1 = document.getElementById("password1").value.trim();
    var password1_validation = document.getElementById("password1-validation");
    var password2 = document.getElementById("password2").value.trim();
    var password2_validation = document.getElementById("password2-validation");

    if (password1.length < 8 || password1.length > 64) {
        password1_validation.classList.add('d-block');
    } else if (password1 !== password2) {
        password1_validation.classList.remove('d-block');
        password2_validation.classList.add('d-block');
    } else {
        password2_validation.classList.remove('d-block');
        var subb = document.getElementById("subb");
        subb.click();
    }
}


function parametersButtonClicked() {
    var height = document.getElementById("inputheight").value.trim();
    var height_validation = document.getElementById("inputheight-validation");
    var weight = document.getElementById("inputweight").value.trim();
    var weight_validation = document.getElementById("inputweight-validation");

    if (height < 0) {
        height_validation.classList.add('d-block');
    } else if (weight < 0) {
        weight_validation.classList.add('d-block');
    } else {
        height_validation.classList.remove('d-block');
        weight_validation.classList.remove('d-block');
        window.location.replace(window.location.href + "/parametry&waga=" + weight + "&wzrost=" + height);
    }
}

function deleteButtonClicked() {
    window.location.replace(window.location.href + "&usun");
}
