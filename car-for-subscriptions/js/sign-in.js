"use strict"


document.addEventListener('DOMContentLoaded', function() { //перевірка на те що документ вже загружений
	const form = document.getElementById('form__sign-in');
	form.addEventListener('submit', formSend); //при відправці форми ми переходимо в функцію formSend

	async function formSend(e){
		e.preventDefault(); //забороняєм стандартну відправку форми

		let email = document.getElementById('exampleInputEmail1').value;
		let password = document.getElementById('exampleInputPassword1').value;
		let url = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/users/' + email + '/' + password;
		alert(url);
		let response = await fetch(url, { //відправка технологією AJAX, за допомогою fetch
			method: 'GET'
		});
		if (response.ok) { //маємо получити відповідь вдала відправка чи ні
			let result = await response.json(); //якщо все ок получаємо певну json відповідь
			sessionStorage.setItem('user'); //через кому потрібно поставити user
			window.location.href = "http://localhost:7886/cabinet-inactive.html";
		}else{
			alert('Email або password бути введені неправильно'); //якщо щось пішло не так - виводиться помилка
		}
	};
})