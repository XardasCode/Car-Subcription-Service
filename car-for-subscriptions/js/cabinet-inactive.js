// Зміна статичних данних на динамічні / редірект в залежності від статусу підписки користувача 

if (sessionStorage.getItem('user') != null) {
    let user = sessionStorage.getItem('user');
    let userJson = JSON.parse(user);
    let subId = userJson['subscriptionId'];
    if (subId == 0) {
        let username = document.getElementById('username');
        let email = document.getElementById('email');
        let phone = document.getElementById('phone');
        let jsonName = userJson['name'];
        let jsonSurname = userJson['surname'];
        let jsonEmail = userJson['email'];
        let jsonPhone = userJson['phone'];
        username.innerHTML = jsonName + ' ' + jsonSurname;
        email.innerHTML = jsonEmail;
        phone.innerHTML = jsonPhone;
    }else if(subId > 0){
        if (sessionStorage.getItem('subscription') == null){
            let getResponse = fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/'+subId)
            .then(response => response.json())
            .then(json => sessionStorage.setItem('subscription', JSON.stringify(json)));
            window.location.href = 'http://localhost:7886/cabinet-active.html';
        }
    }
}else{
    window.location.href = 'http://localhost:7886/sign-in.html';
}


// Можливість редагування імені

function editText() {
  // Отримую посилання на span
  var textElement = document.getElementById("username");
  // Отримую поточний текстовий вміст цього елемента
  var currentText = textElement.innerHTML;
  // Замінюю текст на форму для редагування
  textElement.innerHTML = '<input type="text" id="edit-input" value="' + currentText + '"><button onclick="saveText()">Зберегти</button>';
}

function saveText() {
  var inputElement = document.getElementById("edit-input");
  // Отримую введене користувачем значення
  var newText = inputElement.value;
  // Отримую посилання на HTML-елемент, що містить текст, який ми хочемо редагувати
  var textElement = inputElement.parentNode;
  // Замінюю форму для редагування на нове ім'я
  textElement.innerHTML = newText;
}


//Випадаюче меню:

/////Auto/////

jQuery(($) => {
    $('.inactive__auto').on('click', '.inactive__head-auto', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.inactive__head-auto').removeClass('open');
            $('.inactive__list-auto').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.inactive__auto').on('click', '.inactive__item-auto', function () {
        $('.inactive__head-auto').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.inactive__auto').length) {
            $('.inactive__head-auto').removeClass('open');
            $('.inactive__list-auto').fadeOut();
        }
    });
});

/////Term/////

jQuery(($) => {
    $('.inactive__term').on('click', '.inactive__head-term', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.inactive__head-term').removeClass('open');
            $('.inactive__list-term').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.inactive__term').on('click', '.inactive__item-term', function () {
        $('.inactive__head-term').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.inactive__term').length) {
            $('.inactive__head-term').removeClass('open');
            $('.inactive__list-term').fadeOut();
        }
    });
});

/////Color/////

jQuery(($) => {
    $('.inactive__color').on('click', '.inactive__head-color', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.inactive__head-color').removeClass('open');
            $('.inactive__list-color').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.inactive__color').on('click', '.inactive__item-color', function () {
        $('.inactive__head-color').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.inactive__color').length) {
            $('.inactive__head-color').removeClass('open');
            $('.inactive__list-color').fadeOut();
        }
    });
});


// Валідація полів на заповнення 

document.addEventListener('DOMContentLoaded', function() { //перевірка на те що документ вже загружений
    const form = document.getElementById('form');
    form.addEventListener('submit', formSend); //при відправці форми ми переходимо в функцію formSend

    async function formSend(e){
        e.preventDefault(); //забороняєм стандартну відправку форми

        let error = formValidate(form);

        if(error === 0) {

            /*let response = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/users', { //відправка технологією AJAX, за допомогою fetch
                method: 'POST',
                body: formData
            });
            if (response.ok) { //маємо получити відповідь вдала відправка чи ні
                let result = await response.json(); //якщо все ок получаємо певну json відповідь
                alert(result.message); //виводимо відповідь користувачеві
                form.reset(); //очистка всіх полів форми
            }else{
                alert('Помилка'); //якщо щось пішло не так - виводиться помилка
            }*/

        } else {
            alert("Заповніть обов'язкові поля!")
        }
    }


    function formValidate(form) {
        let error = 0;
        let formReq = document.querySelectorAll('._req'); //required - обов'язкове поле

        for (let index = 0; index < formReq.length; index++) {
            const input = formReq[index];
            formRemoveError(input);

            if(input.value === ''){ //перевірка чи поле заповленене 
                formAddError(input);
                error++;
            }
        }
        return error;
    }
    function formAddError(input) {
        input.parentElement.classList.add('_error'); //добавляю батьківському об'єкту клас error
        input.classList.add('_error'); //добавляю самому об'єкту клас error
    }
    function formRemoveError(input) {
        input.parentElement.classList.remove('_error'); //забираю клас error у батьківського об'єкта
        input.classList.remove('_error'); //забираю клас error у об'єкта 
    }
});


// Валідація номеру паспорта регулярним виразом 

var result = document.querySelector('#result');
var form = document.querySelector('#form');

form.addEventListener('submit', function(e) {
    e.preventDefault();
    checkPassportNumber(this.passport.value);
})

function checkPassportNumber(passportNo){
    var passportRE = /^\d\d\d\d\d\d\d\d-\d\d\d\d\d$/;
    if (passportNo.match(passportRE)) {
        result.innerHTML = 'Номер паспорту введено правильно';
    }else {
        result.innerHTML = 'Номер паспорту введено <strong><u>не правильно</u></strong><br>Приклад: 10101010-10101';
    }
}

// Валідація ІПН регулярним виразом 

var result2 = document.querySelector('#result2');
var form = document.querySelector('#form');

form.addEventListener('submit', function(e) {
    e.preventDefault();
    checkIpnNumber(this.ipn.value);
})

function checkIpnNumber(ipnNo){
    var ipnRE = /^\d\d\d\d\d\d\d\d\d\d$/;
    if (ipnNo.match(ipnRE)) {
        result2.innerHTML = 'Номер паспорту введено правильно';
    }else {
        result2.innerHTML = 'Номер паспорту введено <strong><u>не правильно</u></strong><br>Приклад: 0101010101';
    }
}



/*// Динамічний header в залежності від того, чи користувач залогований

function updateHeader() {
  const headerEl = document.querySelector('.header__last-item');
  const userStr = sessionStorage.getItem('user');
  if (userStr) {
    // Якщо користувач залогований, виводимо кнопки "Мій кабінет/Вийти"
    const user = JSON.parse(userStr);
    headerEl.innerHTML = `
    <li class="nav-item">
      <a class="nav-link active header__cabinet header__active" aria-current="page" href="#">Мій кабінет</a><span class="header__slash">/</span><a class="header__exit" id="logoutButton" href="#">Вийти</a>
    </li>
    `;
    const logoutBtn = headerEl.querySelector('#logout-btn');
    logoutBtn.addEventListener('click', logoutUser);
  } else {
    // Якщо користувач не залогований, виводимо кнопки "Увійти/Зареєструватись"
    headerEl.innerHTML = `
    <li class="nav-item">
      <a class="nav-link active header__sign-in" aria-current="page" href="sign-in.html">Увійти</a><span class="header__slash">/</span><a class="header__sign-up" href="sign-up.html">Зареєструватись</a>
    </li>
    `;
  }
}*/


// Розлогування користувача та редірект на сторінку входу

function logoutUser() { 
  sessionStorage.removeItem('user'); // Видалення з сесії

  window.location.replace('sign-in.html'); // Редірект на сторінку входу
}
