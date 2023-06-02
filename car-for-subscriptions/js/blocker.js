

addEventListener('DOMContentLoaded', async function () {
    let user = sessionStorage.getItem('user');
    await checkUser(user);

});


async function checkUser(user) {
    if (user === null) {
        window.location.href = 'sign-in.html';
    }
    let userJson = JSON.parse(user);
    let userRole = userJson['role']; //  Roles: USER, MANAGER
    if (userRole === 'USER') {
        window.location.href = 'cabinet.html';
    }
}

let lastPage = '';
window.addEventListener("DOMContentLoaded", (event) => {
    getUsers('1',"subscriptionStatus:CONFIRM_STATUS","isBlocked:false");
});

function isBlockOrNotIsBlock(){
    let isBlocked = document.getElementById('block-users');
    let isBlockedBtn = document.getElementById('block-users-btn');
    let selector = document.getElementById('sekectSubStatus');


    if(isBlocked){
  
        selector.style.visibility = 'hidden'
        isBlockedBtn.innerHTML=`<a href="#" class="blue-button not-blocked" id ='unblock-users' onclick="isBlockOrNotIsBlock()" >Не&nbspзаблоковані</a>`
    }else{
      
        selector.style.visibility = 'visible'
        isBlockedBtn.innerHTML=`<a href="#" class="blue-button" id ='block-users' onclick="isBlockOrNotIsBlock()" >Заблоковані</a>`
    }
    filterUsers("1");
}

function filterUsers(pageNumber = '1'){

    let subStatus = document.getElementById('subStatus').value;
    let isBlockedBtn = document.getElementById('block-users');
    let filterStatus = '';

    if(subStatus==='Активна'){
        filterStatus = 'subscriptionStatus:CONFIRM_STATUS';
    }else if(subStatus==='На розгляді'){
        filterStatus = 'subscriptionStatus:UNDER_CONSIDERATION';
    }else{
        filterStatus = 'noSubscription:true';
    }

    let isBlocked = "isBlocked:true";

    if(isBlockedBtn){
        isBlocked = "isBlocked:false";
    }

    filterStatus = filterStatus === '' ? null : filterStatus;
    getUsers(pageNumber,filterStatus, isBlocked);
}


function getUsers(page,subStatus, isBlocked) {

    // const goToActiveSub = document.getElementById('go-to-block-user');

    // if (goToActiveSub) {
    //     goToActiveSub.onclick = function () {
    //         getActiveSubscriptions('1');
    //     }
    //     goToActiveSub.innerHTML = '<a href="#" class="blue-button">Не заблоковаеі</a>';
    // }

    let host = 'https://carfinity-api.lm.r.appspot.com/api/v1/users/search?';
    let myPage = `page=${page}`;
    let size = 'size=2';
    let filter = `filter=${isBlocked},${subStatus}`;
    
    //let urlPage = 'http://localhost:8080/api/v1/subscriptions/page-count?' + size + '&' + filter;
    let urlPage = 'https://carfinity-api.lm.r.appspot.com/api/v1/users/page-count?' + size + '&' + filter;
        fetch(urlPage)
    .then(response => response.json())
    .then(json => generatePageNumber(json, myPage));

    

    let url = host + myPage + '&' + size + '&' + filter;
    fetch(url)
        .then(response => response.json())
        .then(json => generateUsers(json));
}

function generateUsers(json) {
    let subStatus = document.getElementById('subStatus').value;
    let Status = '';
    if(subStatus === 'subscriptionStatus:CONFIRM_STATUS'){
        Status = 'Активна';
    }else if(subStatus === 'subscriptionStatus:UNDER_CONSIDERATION'){
        Status = 'На розгляді';
    }else{
        Status = 'Без підписки';
    }


    const users_container = document.querySelector('#users_container');
    users_container.innerHTML = '';
    json.forEach(item => {
        users_container.innerHTML += ` 
       <div class="manager__container">

                        <div class="cabinet-active__right">
							<div class="cabinet-active__block">
								<div class="cabinet-active__block-text">
									<div class="cabinet-active__status">${item['name']} ${item['surname']}&nbsp; </div>
								</div>

                                <div class="cabinet-active__block-text">
									<div class="cabinet-active__status">Підписка: <span>${Status}</span></div>
								</div>

								<div class="cabinet-active__block-text">
									<div class="cabinet-active__status button-report">
										${item['isBlocked']===false?`<a class="cabinet-active__a-report blue-button" onclick="blockUser(${item['id']})" href="#">Заблокувати</a>`:`<a class="cabinet-active__a-report blue-button" onclick="unblockUser(${item['id']})" href="#">Розблокуати</a>`}
									</div>
								</div>
							</div>				
						</div>
				</div>
        `
    });
}


function generatePageNumber(json, myPage){
    lastPage = json;
    const pageNumber = document.getElementById('page_number');
    pageNumber.innerHTML = '';
    let activePage = myPage.slice(5, myPage.length);

    for (var i = 1; i <= json; i++) {
        if (i == activePage) {
            pageNumber.innerHTML += `<div class="switch-item switch-active" id="activePage">${i}</div>` 
        } else{
            pageNumber.innerHTML += `<div class="switch-item switch-inactive" onclick="filterUsers(${i})">${i}</div>`   
        }
    }
}

function goLeft(){
    let div = document.getElementById('activePage');
    if (div) {
        let page = div.textContent;
        if (page !== '1') {
            filterUsers(page - 1);
        }
    }
}
function goRight(){
    let div = document.getElementById('activePage');
    if (div) {
        let page = div.textContent;
        if (page !== lastPage) {
        let rightPage = (Number(page) + 1).toString(); 
            filterUsers(rightPage);
        }
    }
}

async function blockUser(id){

    const response = await fetch(`https://carfinity-api.lm.r.appspot.com/api/v1/users/${id}/block`, {
        method: 'PATCH'
    });
    let responseJSON = await response.json();
    let status = responseJSON['status'];
    if (status) {
        alert(status);
        location.reload()
    } else {
        let error = responseJSON['errorMessage'];
        alert(error);
    }
}

async function unblockUser(id){
    const response = await fetch(`https://carfinity-api.lm.r.appspot.com/api/v1/users/${id}/unblock`, {
        method: 'PATCH'
    });
    let responseJSON = await response.json();
    let status = responseJSON['status'];
    if (status) {
        alert(status);
        location.reload()
    } else {
        let error = responseJSON['errorMessage'];
        alert(error);
    }
}


// Випадаючий список "статус підписки""

jQuery(($) => {
    $('.blocker__status').on('click', '.blocker__status-head', function () {
        if ($(this).hasClass('open')) {
            $(this).removeClass('open');
            $(this).next().fadeOut();
        } else {
            $('.blocker__status-head').removeClass('open');
            $('.blocker__status-list').fadeOut();
            $(this).addClass('open');
            $(this).next().fadeIn();
        }
    });

    $('.blocker__status').on('click', '.blocker__status-item', function () {
        $('.blocker__status-head').removeClass('open');
        $(this).parent().fadeOut();
        $(this).parent().prev().text($(this).text());
        $(this).parent().prev().prev().val($(this).text());
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('.blocker__status').length) {
            $('.blocker__status-head').removeClass('open');
            $('.blocker__status-list').fadeOut();
        }
    });
});