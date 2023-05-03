// Фільтрація 

// const cars = [...]; // масив об'єктів з даними про авто
let getResponse = fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/cars?page=1&size=12')
.then(response => response.json())
.then(json => generateCars(json));

function generateCars(json) {
    document.querySelector('.catalog__item-list').innerHTML = '';
    json.forEach(item => {
        // let CarPhoto = getCarPhoto(item['id']);
        document.querySelector('.catalog__item-list').innerHTML += ` 
        <div class="col-sm-12 col-md-6 col-xl-4 catalog__block">
            <a href="car-profile.html?id=${item['id']}">
                <img src="img/catalog/svg/car-1.svg" class="adaptive-img" alt="Volkswagen T-Roc">
                <span> ${item['name']}</span>
            </a>
        </div>
        `
    })
}
    
/*async function getCarPhoto(id) {
    let getResponse = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/image/${id}`)
    .then(response => response.arrayBuffer())
    .then(arrayBuffer => {
    const byteArray = new Uint8Array(arrayBuffer);
    console.log(byteArray);
    });
    const blob = new Blob([byteArray], { type: 'image/jpg' });
    const url = URL.createObjectURL(blob);
    return url;
}*/

function filterCarsByYear(year) {
  if (!year) {
    return cars; // повертаємо всі авто, якщо фільтр не вказаний
  }
  
  return cars.filter(car => car.year === year);
}

document.getElementById('year-filter').addEventListener('change', event => {
  const year = event.target.value;
  const filteredCars = filterCarsByYear(year);
  // Відображаємо списки авто, що відповідають вибраним параметрам фільтра
});



///////////////year//////////////////

jQuery(($) => {
    $('.catalog__year').on('click', '.catalog__year-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__year-head').removeClass('open');
            $('.catalog__year-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__year').on('click', '.catalog__year-item', function () {
        $('.catalog__year-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__year').length) {
            $('.catalog__year-head').removeClass('open');
            $('.catalog__year-list').fadeOut();
        }
    });
});

///////////////fuel//////////////////

jQuery(($) => {
    $('.catalog__fuel').on('click', '.catalog__fuel-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__fuel-head').removeClass('open');
            $('.catalog__fuel-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__fuel').on('click', '.catalog__fuel-item', function () {
        $('.catalog__fuel-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__fuel').length) {
            $('.catalog__fuel-head').removeClass('open');
            $('.catalog__fuel-list').fadeOut();
        }
    });
});

///////////////color//////////////////

jQuery(($) => {
    $('.catalog__color').on('click', '.catalog__color-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__color-head').removeClass('open');
            $('.catalog__color-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__color').on('click', '.catalog__color-item', function () {
        $('.catalog__color-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__color').length) {
            $('.catalog__color-head').removeClass('open');
            $('.catalog__color-list').fadeOut();
        }
    });
});

///////////////price//////////////////

jQuery(($) => {
    $('.catalog__price').on('click', '.catalog__price-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__price-head').removeClass('open');
            $('.catalog__price-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__price').on('click', '.catalog__price-item', function () {
        $('.catalog__price-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__price').length) {
            $('.catalog__price-head').removeClass('open');
            $('.catalog__price-list').fadeOut();
        }
    });
});

///////////////brand//////////////////

jQuery(($) => {
    $('.catalog__brand').on('click', '.catalog__brand-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.catalog__brand-head').removeClass('open');
            $('.catalog__brand-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.catalog__brand').on('click', '.catalog__brand-item', function () {
        $('.catalog__brand-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.catalog__brand').length) {
            $('.catalog__brand-head').removeClass('open');
            $('.catalog__brand-list').fadeOut();
        }
    });
});

///////////////search//////////////////

