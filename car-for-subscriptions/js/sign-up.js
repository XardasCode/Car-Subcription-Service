// Відправка форми

document.addEventListener('DOMContentLoaded', function() { //перевірка на те що документ вже загружений
	const form = document.getElementById('form');
	form.addEventListener('submit', formSend); //при відправці форми ми переходимо в функцію formSend

	async function formSend(e){
		e.preventDefault(); //забороняєм стандартну відправку форми
		let error = formValidate(form);
		let name = document.getElementById('userInputName').value;
		let surname = document.getElementById('userInputSurname').value;
		let email = document.getElementById('userInputEmail').value;
		let phone = document.getElementById('userInputNumber').value;
		let password = document.getElementById('userInputPassword').value;
		
		let test = {
			"name": name,
			"surname": surname,
			"email": email,
			"phone": phone,
			"password": password
		}

		if(error === 0) {

		const response = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/users', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(test)
		});
		let responseJSON = await response.json();
		let status = responseJSON['status'];
			if (status === 'success') {
			let id = responseJSON['message'];
			let getResponse = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/users/'+id)
	        .then(response => response.json())
	        .then(json => sessionStorage.setItem('user', JSON.stringify(json)));
	        window.location.href = 'http://localhost:7886/cabinet-inactive.html';
			}else{
				let error = responseJSON['errorMessage'];
				alert(error);
			}
		}
	}


	// Валідація phone, password, email, checkbox, перевірка на заповлення поля

	function formValidate(form) {
		let error = 0;
		let formReq = document.querySelectorAll('._req'); //required - обов'язкове поле

		for (let index = 0; index < formReq.length; index++) {
			const input = formReq[index];
			formRemoveError(input);
			if (input.classList.contains('_phone')) { //перевірка phone
				formAddError(input);
				let phoneRE = /^\(\d{3}\)\s?\d{3}-\d{4}$/;
				let phone = document.getElementById('userInputNumber').value;
				let result = document.querySelector('#result');
			    if (phone.match(phoneRE)) {
			        result.innerHTML = 'Номер телефону введено правильно';
			    }else {
			    	formAddError(input);
					error++;
			        result.innerHTML = 'Номер телефону введено <strong><u>не правильно</u></strong><br>Приклад: (XXX) XXX-XXXX';
			    }
			}
			if (input.classList.contains('_password')) { //перевірка password
				let password = document.getElementById('userInputPassword').value;
				let repeatPassword = document.getElementById('userInputPasswordRepeat').value;
				if (password !== repeatPassword) {
					formAddError(input);
					error++;
					alert('Паролі не збігаються');
				}
			}
			if (input.classList.contains('_email')) { //перевірка email
				if (emailTest(input)){
					formAddError(input); //добавлення класу error
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
		return !/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,8})+$/.test(input.value); //перевірка на відповідність чи є різні символи
	}
	function phoneTest(input) {
		return /^\(\d{3}\)\s?\d{3}-\d{4}$/.test(input.value); 
	}

});


// Редірект користувача в кабінет, при спробі повернутись до сторінки реєстрації

if (sessionStorage.getItem('user') != null) {
	window.location.href = 'cabinet-inactive.html';
}