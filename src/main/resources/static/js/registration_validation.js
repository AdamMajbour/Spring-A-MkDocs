const form = document.getElementById('form');
const name = document.getElementById('name');
const surname = document.getElementById('surname');
const login = document.getElementById('login');
const email = document.getElementById('email');
const password = document.getElementById('password');
const confirmPassword = document.getElementById('confirm-password');
const button = document.getElementById('sub-button')
const submit = document.getElementById('submit')

const emptyErrorMessage = 'To pole nie może byc puste!!!'

var values = [name, surname, login, email, password, confirmPassword]
var flag = true;



button.addEventListener('click', (e) => {
    e.preventDefault();
    console.log("weszło");
    flag=true;
    values.forEach(element => {
        if (element.value.trim() === "")
        {
            setError(element, emptyErrorMessage);
            flag = false;
        }
        else
            setSucces(element);
    });
    if(login.value.trim().length < 4 || login.value.trim().length > 32)
    {
        setError(login, 'Login powinien zawierać od 4 do 32 znaków!!!');
        flag=false;
    }
    if(password.value.trim().length < 8 || password.value.trim().length > 64)
    {
        setError(password, 'Hasło powinien zawierać od 8 do 64 znaków!!!');
        flag=false;
    }
    if (confirmPassword.value.trim() !== '' &&
        confirmPassword.value.trim() !== password.value.trim())
    {
        setError(confirmPassword, 'Hasła nie moga być różne!!!');
        flag=false;
    }
    if (!validateEmail(email.value.trim()))
    {
        setError(email, 'Email musi mieć strukturę s@s.s');
        flag=false;
    }
    if(flag)
        submit.click();

})

function setError(where, errorMessage) {
    const formControl = where.parentElement;
    const err = formControl.querySelector('a');

    err.innerText = errorMessage;
    formControl.className = 'text_box err'
}

function setSucces(where) {
    const formControl = where.parentElement;
    formControl.className = 'text_box succes'
}

function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}
