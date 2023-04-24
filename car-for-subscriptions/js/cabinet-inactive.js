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


/////Validate number/////

/*const validator = new JustValidate('#form');

validator.addField('#passport', [
  {
    rule: 'minNumber',
    value: 13,
  },
  {
    rule: 'maxNumber',
    value: 14,
    errorMassage: "Поле має містити максимум 14 символів",
  },
  {
    rule: 'number',
    errorMassage: "Значення повинне бути числом",
  },
  {
    rule: 'required',
    errorMassage: "Заповніть обов'язкове поле",
  },
]);

const validator2 = new JustValidate2('#form');

validator2.addField('#ipn', [
  {
    rule: 'minNumber',
    value: 10,
  },
  {
    rule: 'maxNumber',
    value: 10,
    errorMassage: "Поле має містити максимум 10 символів",
  },
]);*/



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
            if (response.ok) {                          //маємо получити відповідь вдала відправка чи ні
                let result = await response.json(); //якщо все ок получаємо певну json відповідь
                alert(result.message);                  //виводимо відповідь користувачеві
                form.reset();                               //очистка всіх полів форми
            }else{
                alert('Помилка');                           //якщо щось пішло не так - виводиться помилка
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