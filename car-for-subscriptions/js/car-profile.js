// Динамічна генерація даних

window.addEventListener("DOMContentLoaded", async (event) => {
    let carId = window.location.search.substr(1);
    carId = carId.slice(3, carId.length);
    if (sessionStorage.getItem('user') != null) {
        let user = sessionStorage.getItem('user');
        let userJson = JSON.parse(user);
        let subId = userJson['subscriptionId'];
        let button = document.getElementById('button');
        if (subId === 0) {
            button.innerHTML = `<a class="sub-enter__button-rectangle" href="cabinet.html?carId=${carId}">Оформити підписку</a>`
        } else {
            button.style.display = 'none';
        }
    }
    if (carId) {
        await displayCar(carId)
    } else {
        window.location.replace('error.html')
    }
});

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
