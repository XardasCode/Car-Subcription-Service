// Динамічна генерація даних

window.addEventListener("DOMContentLoaded", (event) => {
    let carId = window.location.search.substr(1);
    carId = carId.slice(3, carId.length);
    if (sessionStorage.getItem('user') != null) {
    let user = sessionStorage.getItem('user');
    let userJson = JSON.parse(user);
    let subId = userJson['subscriptionId'];
    button = document.getElementById('button');
    if (subId == 0) {
        button.innerHTML = `<a class="sub-enter__button-rectangle" href="cabinet-inactive.html?carId=${carId}">Оформити підписку</a>`
    }else{
        button.style.display = 'none';
    }
}
    if (carId) {
        displayCar(carId)
    }else{
        window.location.replace('error.html')
    }
});

async function displayCar(carId) {
    const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/${carId}`);
    const car = await response.json();
    console.log(car)
    let picture = document.getElementById('picture');
    let brand = document.getElementById('brand');
    let model = document.getElementById('model');
    let regNumber = document.getElementById('regNumber');
    let year = document.getElementById('year');
    let color = document.getElementById('color');
    let fuel = document.getElementById('fuel');
    let period = document.getElementById('period');
    let monthPrice = document.getElementById('monthPrice');
    let firstPayment = document.getElementById('firstPayment');
    brand.innerHTML = car['brand'];
    model.innerHTML = car['name'] + ' ' + car['model'];
    let image = car['image'] == null ? 'img/catalog/svg/car-8.svg' : car['image'];
    picture.src = image;
    year.innerHTML = car['year'];
    color.innerHTML = car['color'];
    fuel.innerHTML = car['fuelType'];
    regNumber.innerHTML = car['regNumber'];
    mileage.innerHTML = car['mileage'];
    monthPrice.innerHTML = '$' + car['price'];
}

/*if (sessionStorage.getItem('user') != null) {
    let user = sessionStorage.getItem('user');
    let userJson = JSON.parse(user);
    let subId = userJson['subscriptionId'];
    if (subId == 0) {

        let jsonPicture = userJson['picture'];
        let jsonBrand = userJson['brand'];
        let jsonModel = userJson['model'];
        let jsonVolume = userJson['volume'];
        let jsonYear = userJson['year'];
        let jsonColor = userJson['color'];
        let jsonFuel = userJson['fuel'];
        let jsonPeriod = userJson['period'];
        let jsonMonthPrice = userJson['monthPrice'];
        let jsonFirstPayment = userJson['firstPayment'];
        picture.innerHTML = jsonPicture;
        brand.innerHTML = jsonBrand;*/
        
    /*}else if(subId > 0){
        if (sessionStorage.getItem('subscription') == null){
            let getResponse = fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/'+subId)
            .then(response => response.json())
            .then(json => sessionStorage.setItem('subscription', JSON.stringify(json)));
            window.location.href = 'http://localhost:7886/cabinet-active.html';
        }
    }
}else{
    window.location.href = 'http://localhost:7886/sign-in.html';
}*/