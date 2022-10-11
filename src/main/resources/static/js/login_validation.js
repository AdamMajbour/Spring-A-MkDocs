const form = document.getElementById('form');
const login = document.getElementById('login');
const password = document.getElementById('password');
const button = document.getElementById('sub-button')
const submit = document.getElementById('submit')

const emptyErrorMessage = 'To pole nie może byc puste!!!'

var values = [login, password]
var flag = true;

button.addEventListener('click', (e) => {
    e.preventDefault();
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
