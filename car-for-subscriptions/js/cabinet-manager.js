// Можливість редагування імені

function editText() {
    // Отримую посилання на span
    let textElement = document.getElementById("username");
    // Отримую поточний текстовий вміст цього елемента
    let currentText = textElement.innerHTML;
    // Замінюю текст на форму для редагування
    textElement.innerHTML = '<input type="text" id="edit-input" value="' + currentText + '"><button onclick="saveText()">Зберегти</button>';
}

function saveText() {
    let inputElement = document.getElementById("edit-input");
    // Отримую введене користувачем значення
    let newText = inputElement.value;
    // Отримую посилання на HTML-елемент, що містить текст, який ми хочемо редагувати
    let textElement = inputElement.parentNode;
    // Замінюю форму для редагування на нове ім'я
    textElement.innerHTML = newText;
}


function showMore(id) {

    const itemWrapper = document.getElementById(`item-wrapper-${id}`);
    const toggleBtn = document.getElementById(`toggle-btn-${id}`);
    const activeBtn = document.getElementById(`active-btn-${id}`);
    const regBtn = document.getElementById(`reg-btn-${id}`);

    if (itemWrapper.classList.contains('open')) {
        // згорнути текст
        if (activeBtn) {
            activeBtn.classList.add('display-none');
        }
        regBtn.classList.add('display-none');
        itemWrapper.classList.remove('open');
        toggleBtn.innerHTML = 'Показати більше';
    } else {
        // розгорнути текст
        if (activeBtn) {
            activeBtn.classList.remove('display-none');
        }
        regBtn.classList.remove('display-none');
        itemWrapper.classList.add('open');
        toggleBtn.innerHTML = 'Показати менше';
    }

}


// // #2
// const itemWrapper2 = document.getElementById('item-wrapper-2');
// const toggleBtn2 = document.getElementById('toggle-btn-2');
// const activeBtn2 = document.getElementById('active-btn-2');

// toggleBtn2.addEventListener('click', function() {
//   if (itemWrapper2.classList.contains('open')) {
//     // згорнути текст
//     activeBtn2.classList.add('display-none');
//     itemWrapper2.classList.remove('open');
//     toggleBtn2.innerHTML = 'Показати більше';
//   } else {
//     // розгорнути текст
//     activeBtn2.classList.remove('display-none');
//     itemWrapper2.classList.add('open');
//     toggleBtn2.innerHTML = 'Показати менше';
//   }
// });

// // #2
// const itemWrapper3 = document.getElementById('item-wrapper-3');
// const toggleBtn3 = document.getElementById('toggle-btn-3');
// const activeBtn3 = document.getElementById('active-btn-3');

// toggleBtn3.addEventListener('click', function() {
//   if (itemWrapper3.classList.contains('open')) {
//     // згорнути текст
//     activeBtn3.classList.add('display-none');
//     itemWrapper3.classList.remove('open');
//     toggleBtn3.innerHTML = 'Показати більше';
//   } else {
//     // розгорнути текст
//     activeBtn3.classList.remove('display-none');
//     itemWrapper3.classList.add('open');
//     toggleBtn3.innerHTML = 'Показати менше';
//   }
// });


window.addEventListener("DOMContentLoaded", (event) => {
    getNotActiveSubscriptions('1');
});


function getNotActiveSubscriptions(page) {

    const goToActiveSub = document.getElementById('go-to-active-sub');

    if (goToActiveSub) {
        goToActiveSub.onclick = function () {
            getActiveSubscriptions('1');
        }
        goToActiveSub.innerHTML = '<a href="#" class="blue-button">Активні підписки</a>';
    }

    let host = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/search?';
    let myPage = `page=${page}`;
    let size = 'size=12';
    let filter = "filter=isActive:false,statusName:Under consideration";
    let url = host + myPage + '&' + size + '&' + filter;
    fetch(url)
        .then(response => response.json())
        .then(json => generateSubscriptions(json));
}

function getActiveSubscriptions(page) {

    const goToNotActiveSub = document.getElementById('go-to-active-sub');
    if (goToNotActiveSub) {
        goToNotActiveSub.onclick = function () {
            getNotActiveSubscriptions('1');
        }
        goToNotActiveSub.innerHTML = '<a href="#" class="blue-button">Неактивні підписки</a>';
    }

    let host = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/search?';
    let myPage = `page=${page}`;
    let size = 'size=12';
    let filter = "filter=isActive:true,statusName:Confirmed";
    let url = host + myPage + '&' + size + '&' + filter;
    fetch(url)
        .then(response => response.json())
        .then(json => generateSubscriptions(json));
}


async function getCar(id) {
    const responseCar = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/' + id);
    return await responseCar.json();
}

async function getUser(id) {
    const responseUser = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/users/' + id);
    return await responseUser.json();
}

async function ptintSub(item, list) {
    const user = await getUser(item['userId']);
    const car = await getCar(item['carId']);
    console.log(user);

    if (item['isActive'] === false) {
        if (item['status'] === "Rejected") {
            list.innerHTML += `
      <div class="collapsible__item-wrapper" id="item-wrapper-${item['id']}">
              <div class="collapsible__info">
                <ul class="collapsible__list">
                  <li>Статус заявки: Вдхилено</li>
                  <li>Заявник: ${user['surname']} ${user['name']}</li>
                    <li>Авто: ${car['name']} ${car['brand']}</li>
                    <li>Кількість місяців: ${item['totalMonths']} </li>
                    <li>Номер телефону: ${user['phone']}</li>
                    <li>Email: ${user['email']}</li>
                  </ul>
              </div>
              <div class="collapsible__buttons">
                <button class="toggle-btn" onclick="showMore(${item['id']})" id="toggle-btn-${item['id']}">Показати більше</button>
                <div class="collapsible__active-sub display-none" onclick="confirmSubscription(${item['id']})" id="active-btn-${item['id']}">
            <a href="#" class="blue-button">Активувати</a>
            </div>
            <div class="collapsible__active-sub display-none" onclick="deleteSubscription(${item['id']})"  id="reg-btn-${item['id']}">
            <a href="#" class="blue-button">Видалити</a>
          </div>
          </div>	 
        </div>
      `;
        } else {
            list.innerHTML += `
      <div class="collapsible__item-wrapper" id="item-wrapper-${item['id']}">
              <div class="collapsible__info">
                <ul class="collapsible__list">
                  <li>Статус заявки: На розгляді</li>
                  <li>Заявник: ${user['surname']} ${user['name']}</li>
                    <li>Авто: ${car['name']} ${car['brand']}</li>
                    <li>Кількість місяців: ${item['totalMonths']} </li>
                    <li>Номер телефону: ${user['phone']}</li>
                    <li>Email: ${user['email']}</li>
                  </ul>
              </div>
              <div class="collapsible__buttons">
                <button class="toggle-btn" onclick="showMore(${item['id']})" id="toggle-btn-${item['id']}">Показати більше</button>
                <div class="collapsible__active-sub display-none" onclick="confirmSubscription(${item['id']})" id="active-btn-${item['id']}">
            <a href="#" class="blue-button">Активувати</a>
            </div>
            <div class="collapsible__active-sub display-none" onclick="rejectSubscription(${item['id']})"  id="reg-btn-${item['id']}">
            <a href="#" class="blue-button">Відхилити</a>
          </div>
          </div>	 
        </div>
      `;
        }

    } else {
        list.innerHTML += `
  <div class="collapsible__item-wrapper" id="item-wrapper-${item['id']}">
           <div class="collapsible__info">
             <ul class="collapsible__list">
               <li>Статус заявки: Активна</li>
               <li>Заявник: ${user['surname']} ${user['name']}</li>
                 <li>Авто: ${car['name']} ${car['brand']}</li>
                 <li>Кількість місяців: ${item['totalMonths']} </li>
                 <li>Номер телефону: ${user['phone']}</li>
                 <li>Email: ${user['email']}</li>
              </ul>
           </div>
           <div class="collapsible__buttons">
             <button class="toggle-btn" onclick="showMore(${item['id']})" id="toggle-btn-${item['id']}">Показати більше</button>
        <div class="collapsible__active-sub display-none" onclick="rejectSubscription(${item['id']})"  id="reg-btn-${item['id']}">
        <a href="#" class="blue-button">Припинити</a>
      </div>
      </div>	 
    </div>
  `;

    }

}

function generateSubscriptions(json) {
    const list = document.querySelector('#sub-list');
    if (list) {
        list.innerHTML = '';
        json.forEach(item => {
            ptintSub(item, list)
        });

    }
}


async function confirmSubscription(id) {
    const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/${id}/confirm`, {
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

async function rejectSubscription(id) {
    const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/${id}/reject`, {
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

async function deleteSubscription(id) {
    alert("Ще не реалізовано на бекенді")
    // const response = await fetch(`https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/${id}`, {
    //   method: 'DELETE'});
    //   let responseJSON = await response.json();
    //   let status = responseJSON['status'];
    //   if(status){
    // 		alert(status);

    //   }else{
    //     let error = responseJSON['errorMessage'];
    // 		alert(error);
    //   }
}

