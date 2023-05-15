async function addCar(){
    let newCar = {
        "name": document.getElementById('name').value,
        "model": document.getElementById('model').value,
        "brand": document.getElementById('brand').value,
        "year": document.getElementById('year').value,
        "color": document.getElementById('color').value,
        "price": document.getElementById('price').value,
        "fuelType": document.getElementById('fuelType').value,
        "chassisNumber": document.getElementById('chassisNumber').value,
        "regNumber":document.getElementById('regNumber').value,
        "regDate": document.getElementById('regDate').value,
        "mileage": document.getElementById('mileage').value,
        "lastServiceDate":document.getElementById('lastServiceDate').value,
        "statusId": "1",
        "image":document.getElementById('image').value 
    };
    

    const response = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/cars', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(newCar)
	});
	let responseJSON = await response.json();
	let status = responseJSON['status'];
    if (status === 'success') {
        let message = responseJSON['message'];
		alert(message);
	}else{
		let error = responseJSON['errorMessage'];
		alert(error);
	}
}

// получаю input file в змінну
const image = document.getElementById('image');
// получаю div для превью в змінну
const preview = document.getElementById('preview');

image.addEventListener('change', () => {
    uploadFile(image.files[0]);
});
function uploadFile(file) {
    // перевірка на тип файлу
    if (!['image/jpeg', 'image/png', 'image/gif'].includes(file.type)) {
        alert('Дозволені тільки зображення!');
        image.value = '';
        return;
    }
    // перевірка на розмір файлу
    if (file.size > 2 * 1024 * 1024) {
        alert('Файл повинен бути меньше 2 Мб!');
        return;
    }

    // конструкція FileReader
    let reader = new FileReader();
    reader.onload = function (e) {
        preview.innerHTML = `<img src="${e.target.result}" alt="Фото">`;
    };
    reader.onerror = function (e) {
        alert("Помилка");
    };
    reader.readAsDataURL(file);
}