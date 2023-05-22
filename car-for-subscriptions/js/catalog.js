// Фільтрація/Пошук/Пагінація

let lastPage = '';

window.addEventListener("DOMContentLoaded", (event) => {
    getCars('1', null, null, null, null, null);
});

document.getElementById('model-filter').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
      filterCars();
    }
});

function filterCars(pageNumber = '1'){
    let model = document.getElementById('model-filter').value;
    let year = document.getElementById('year-filter').value;
    let color = document.getElementById('color-filter').value;
    let price = document.getElementById('price-filter').value;
    let brand = document.getElementById('brand-filter').value;

    model = model === '' ? null : model;
    year = year === '' ? null : year;
    color = color === '' ? null : color;
    price = price === '' ? null : price.slice(0, -1);
    brand = brand === '' ? null : brand;
    getCars(pageNumber, model, year, color, price, brand);
}

function getCars(page, model, year, color, price, brand) {
    let host = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/cars?';
    let myPage = `page=${page}`;
    let size = 'size=12';
    let filter = "";
    if(year != null || color != null || price != null || brand != null || model != null){
        filter = `filter=`;
        if (model != null) {
            filter += `model:${model},`;
        }
        if (year != null) {
            filter += `year:${year},`;
        }
        if (color != null) {
            filter += `color:${color},`;
        }
        if (price != null) {
            filter += `price:${price},`;
        }
        if (brand != null) {
            filter += `brand:${brand},`;
        }
        filter.slice(0, -1);
    }
    let urlPage = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/page-count?' + size + '&' + filter;
     fetch(urlPage)
    .then(response => response.json())
    .then(json => generatePageNumber(json, myPage));

    let url = host + myPage + '&' + size + '&' + filter;
    fetch(url)
    .then(response => response.json())
    .then(json => generateCars(json));
}

function generateCars(json) {
    document.querySelector('.catalog__item-list').innerHTML = '';
    json.forEach(item => {
        let image = item['image'] == null ? 'img/catalog/svg/car-8.svg' : item['image'];
        document.querySelector('.catalog__item-list').innerHTML += ` 
        <div class="col-sm-12 col-md-6 col-xl-4 catalog__block card">
            <a href="car-profile.html?id=${item['id']}">
                <img src="${image}" class="adaptive-img card-img-top" alt="Volkswagen T-Roc">
                <div class="card-body">
                <h5 class="card-title">${item.name} ${item.model} ${item.brand}</h5>
              </div>
            </a>
        </div>
        `
    });
}

function generatePageNumber(json, myPage){
    lastPage = json;
    const pageNumber = document.getElementById('page_number');
    pageNumber.innerHTML = '';
    let activePage = myPage.slice(5, myPage.length);
    // setArrows(activePage, json);

    for (var i = 1; i <= json; i++) {
        if (i == activePage) {
            pageNumber.innerHTML += `<div class="catalog__switch-item switch-active" id="activePage">${i}</div>` 
        } else{
            pageNumber.innerHTML += `<div class="catalog__switch-item switch-item" onclick="filterCars(${i})">${i}</div>`   
        }
    }
}

/*function setArrows(activePage, json){
    const arrowLeft = document.getElementById('arrow-left');
    const arrowRight = document.getElementById('arrow-right'); 
    let page = Number(activePage);
    if (activePage != 1) {
        let leftPage = (page - 1).toString(); 
        arrowLeft.addEventListener("click", function() {
            filterCars(leftPage);
        });
    }
    if (activePage != json){
        arrowRight.addEventListener("click", changePage(activePage + 1));
    }
}*/

function goLeft(){
    let div = document.getElementById('activePage');
    if (div) {
        let page = div.textContent;
        if (page !== '1') {
        filterCars(page - 1);
        }
    }
}
function goRight(){
    let div = document.getElementById('activePage');
    if (div) {
        let page = div.textContent;
        if (page !== lastPage) {
        let rightPage = (Number(page) + 1).toString(); 
        filterCars(rightPage);
        }
    }
}


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