// Динамічна генерація даних

window.addEventListener("DOMContentLoaded", async (event) => {
    let carId = window.location.search.substr(1);
    if (carId == null || carId === "") {
        window.location.replace('error.html')
    }
    carId = carId.slice(3, carId.length);
    if (sessionStorage.getItem('user') != null) {
        await setButtonBySubscriptionAndRole(carId);
    }
    if (carId) {
        await displayCar(carId)
    }
});

async function setButtonBySubscriptionAndRole(carId) {
    let user = sessionStorage.getItem('user');
    let userJson = JSON.parse(user);
    let userRole = userJson['role']; //  Roles: USER, MANAGER
    if (userRole === "USER") {
        let subId = userJson['subscriptionId'];
        await setSubscriptionButton(subId, carId);
    } else { // MANAGER
        let button = document.getElementById('button');
        button.innerHTML = `<a class="sub-enter__button-rectangle" href="add-panel.html?carId=${carId}">Оновити дані</a>`;
    }
}

async function setSubscriptionButton(subId, carId) {
    let button = document.getElementById('button');
    if (subId === 0) {
        let button = document.getElementById('button');
        button.innerHTML = `<a class="sub-enter__button-rectangle" href="cabinet.html?carId=${carId}">Оформити підписку</a>`;
    } else {
        button.style.display = 'none';
    }
}

async function displayCar(carId) {
    const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/${carId}`);
    const car = await response.json();

    let picture = document.getElementById('picture');
    let brand = document.getElementById('brand');
    let model = document.getElementById('model');
    let regNumber = document.getElementById('regNumber');
    let year = document.getElementById('year');
    let color = document.getElementById('color');
    let fuel = document.getElementById('fuel');
    let monthPrice = document.getElementById('monthPrice');

    brand.innerHTML = car['brand'];
    model.innerHTML = car['name'] + ' ' + car['model'];
    picture.src = car['image'] == null ? 'img/catalog/svg/car-8.svg' : car['image'];
    year.innerHTML = car['year'];
    color.innerHTML = car['color'];
    fuel.innerHTML = car['fuelType'];
    regNumber.innerHTML = car['regNumber'];
    mileage.innerHTML = car['mileage'];
    monthPrice.innerHTML = '$' + car['price'];
}
