addEventListener('DOMContentLoaded', async function () {
    // получаю input file в змінну
    const image = document.getElementById('image');
    // получаю div для превью в змінну
    const preview = document.getElementById('preview');

    image.addEventListener('change', () => {
        uploadFile(image.files[0]);
    });

    let carId = new URLSearchParams(window.location.search).get('carId');
    if (carId != null) {
        await setCarInfoToForm(carId);
    }


    function uploadFile(file) {
        // перевірка на тип файлу
        if (!['image/jpeg', 'image/png', 'image/gif'].includes(file.type)) {
            alert('Дозволені тільки зображення!');
            image.value = '';
            return;
        }
        // перевірка на розмір файлу
        if (file.size > 2 * 1024 * 1024) {
            alert('Файл повинен бути менше 2 Мб!');
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

    let form = document.getElementById('formUpload');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        let isUpdate = carId != null;
        await sendForm(isUpdate);
    });
});

async function setCarInfoToForm(carId) {
    let car = await getCarFromServer(carId);
    document.getElementById('name').value = car['name'];
    document.getElementById('model').value = car['model'];
    document.getElementById('brand').value = car['brand'];
    document.getElementById('year').value = car['year'];
    document.getElementById('color').value = car['color'];
    document.getElementById('price').value = car['price'];
    document.getElementById('fuelType').value = car['fuelType'];
    document.getElementById('chassisNumber').value = car['chassisNumber'];
    document.getElementById('regNumber').value = car['regNumber'];
    document.getElementById('regDate').value = car['regDate'];
    document.getElementById('mileage').value = car['mileage'];
    document.getElementById('lastServiceDate').value = car['lastServiceDate'];
}

async function getCarFromServer(carId) {
    const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/${carId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    });
    let responseJSON = await response.json();
    let status = response.status;
    if (status > 299) {
        let error = responseJSON['errorMessage'];
        alert(error);
    } else {
        console.log("Car received");
        return responseJSON;
    }
}

async function sendForm(isUpdate) {
    let newCar = {
        "name": document.getElementById('name').value,
        "model": document.getElementById('model').value,
        "brand": document.getElementById('brand').value,
        "year": document.getElementById('year').value,
        "color": document.getElementById('color').value,
        "price": document.getElementById('price').value,
        "fuelType": document.getElementById('fuelType').value,
        "chassisNumber": document.getElementById('chassisNumber').value,
        "regNumber": document.getElementById('regNumber').value,
        "regDate": document.getElementById('regDate').value,
        "mileage": document.getElementById('mileage').value,
        "lastServiceDate": document.getElementById('lastServiceDate').value
    };
    if (isUpdate) {
        let carId = new URLSearchParams(window.location.search).get('carId');
        await updateCar(newCar, carId);
    } else {
        await addNewCar(newCar)
    }
}

async function updateCar(newCar, carId) {
    const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/${carId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newCar)
    });
    let responseJSON = await response.json();
    let status = response.status;
    if (status > 299) {
        let error = responseJSON['errorMessage'];
        alert(error);
    } else {
        console.log("Car updated");
        let isPhotoUpdated = document.getElementById('image').files.length > 0;
        if (isPhotoUpdated) {
            await sendPhotoToServer(carId);
        }
        window.location.replace('car-profile.html?id=' + carId);
    }
}

async function addNewCar(newCar) {
    const response = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/cars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newCar)
    });
    let responseJSON = await response.json();
    let status = response.status;
    if (status > 299) {
        let error = responseJSON['errorMessage'];
        alert(error);
    } else {
        console.log("Car added");
        await sendPhotoToServer(responseJSON['message']);
        window.location.replace('car-profile.html?id=' + responseJSON['message']);
    }
}

async function sendPhotoToServer(carId) {
    console.log("Car id: " + carId);
    let formData = new FormData();
    let file = document.getElementById('image').files[0];
    formData.append('imageFile', file);

    const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/image/${carId}`, {
        method: 'POST',
        body: formData
    });
    let responseJSON = await response.json();
    let status = response.status;
    if (status > 299) {
        let error = responseJSON['errorMessage'];
        alert(error);
    }
    console.log("Photo uploaded");
}
