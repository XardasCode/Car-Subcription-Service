// "use strict"

document.addEventListener('DOMContentLoaded', function() { //перевірка на те що документ вже загружений
	const form = document.getElementById('form');
	form.addEventListener('submit', formSend); //при відправці форми ми переходимо в функцію formSend

	async function formSend(e){
		e.preventDefault(); //забороняєм стандартну відправку форми

		let error = formValidate(form);

		// let formData = new FormData(form); //за допомогою FormData витягуєм всі дані з полів
		let name = document.getElementById('userInputName').value;
		let surname = document.getElementById('userInputSurname').value;
		// let date = document.getElementById('userInputDate').value;
		let email = document.getElementById('userInputEmail').value;
		let phone = document.getElementById('userInputNumber').value;
		let password = document.getElementById('userInputPassword').value;
		
		let test = {
			"name": name,
			"surname": surname,
			// "date": date,
			"email": email,
			"phone": phone,
			"password": password
		}

		if(error === 0) {

			let response = fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/users', { //відправка технологією AJAX, за допомогою fetch
				method: 'POST',
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(test)
			});
			if (response.ok) { //маємо получити відповідь вдала відправка чи ні
				let result = response.json(); //якщо все ок получаємо певну json відповідь
				let id = result.message;

				let getResponse = fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/users/'+id)
            .then(response => response.json())
            .then(json => sessionStorage.setItem('user', JSON.stringify(json)));
            window.location.href = 'http://localhost:7886/cabinet-inactive.html';
				
			}else{
				alert('Помилка'); //якщо щось пішло не так - виводиться помилка
			}

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

			if (input.classList.contains('_email')) { //перевірка email
				if (emailTest(input)){
					formAddError(input); //добавляю клас error
					error++;
				}
			}else if(input.getAttribute("type") === "checkbox" && input.checked === false){ //перевірка на тип + чи цей checkbox не включений
				formAddError(input);
				error++;
			}else{
				if(input.value === ''){ //перевірка чи поле взагалі заповленене 
					formAddError(input);
					error++;
				}
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
	function emailTest(input) {
		return !/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,8})+$/.test(input.value); //регулярним вирозом перевіряє на відповідність чи є різні символи
	}
});


// Валідація номеру телефона регулярним виразом

var result = document.querySelector('#result');
var form = document.querySelector('#form');

form.addEventListener('submit', function(e) {
    e.preventDefault();
    let userInputNumber = document.getElementById('userInputNumber');
    checkPhoneNumber(userInputNumber.value);
})

function checkPhoneNumber(phoneNo){
    var phoneRE = /^\(\d\d\d\) \d\d\d-\d\d\d\d$/;
    if (phoneNo.match(phoneRE)) {
        result.innerHTML = 'Номер телефону введено правильно';
    }else {
        result.innerHTML = 'Номер телефону введено <strong><u>не правильно</u></strong><br>Приклад: (XXX) XXX-XXXX';
    }
}