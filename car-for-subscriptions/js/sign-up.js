"use strict"

document.addEventListener('DOMContentLoaded', function() { //перевірка на те що документ вже загружений
	const form = document.getElementById('form');
	form.addEventListener('submit', formSend); //при відправці форми ми переходимо в функцію formSend

	async function formSend(e){
		e.preventDefault(); //забороняєм стандартну відправку форми

		let error = formValidate(form);

		/*let formData = new FormData(form);*/ //за допомогою FormData витягуєм всі дані з полів

		if(error === 0) {

			/*let response = await fetch('файл', { //відправка технологією AJAX, за допомогою fetch
				method: 'POST',
				body: formData
			});
			if (response.ok) { 							//маємо получити відповідь вдала відправка чи ні
				let result = await response.json(); //якщо все ок получаємо певну json відповідь
				alert(result.message);					//виводимо відповідь користувачеві
				form.reset();								//очистка всіх полів форми
			}else{
				alert('Помилка');							//якщо щось пішло не так - виводиться помилка
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
