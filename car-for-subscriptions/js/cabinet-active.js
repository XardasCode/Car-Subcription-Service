// if (sessionStorage.getItem('user') != null) {
//     let user = sessionStorage.getItem('user');
//     let userJson = JSON.parse(user);
//     let subId = userJson['subscriptionId'];
//     if (subId == 0) {
//     window.location.href = 'cabinet.html';
//     }else if(subId > 0){
//         if (sessionStorage.getItem('subscription') == null) {
//             let getResponse = await fetch('https://carfinity-api.lm.r.appspot.com/api/v1/subscriptions/'+subId) 
//             .then(response => response.json())
//             .then(json => sessionStorage.setItem('subscription', JSON.stringify(json)));

//             window.location.href = 'cabinet-active.html';
//         }
//     }

// }else{
//     window.location.href = 'sign-in.html';
// }zz


// Динамічний header в залежності від того, чи користувач залогований

/*  const headerEl = document.querySelector('.header__last-item');
  const userStr = sessionStorage.getItem('user');
  if (userStr) {
    // Якщо користувач залогований, виводимо кнопки "Мій кабінет/Вийти"
    const user = JSON.parse(userStr);
    headerEl.innerHTML = `
    <li class="nav-item">
      <a class="nav-link active header__cabinet" aria-current="page" href="cabinet-active.html">Мій кабінет</a><span class="header__slash">/</span><a class="header__exit" href="#" onclick="logoutUser()">Вийти</a>
    </li>
    `;
    const logoutBtn = headerEl.querySelector('#logout-btn');
    if (logoutBtn) {
      el.addEventListener('click', swapper, false);
    }
    
  } else {
    // Якщо користувач не залогований, виводимо кнопки "Увійти/Зареєструватись"
    headerEl.innerHTML = `
    <li class="nav-item">
      <a class="nav-link active header__sign-in" aria-current="page" href="sign-in.html">Увійти</a><span class="header__slash">/</span><a class="header__sign-up" href="sign-up.html">Зареєструватись</a>
    </li>
    `;
  }*/


// Розлогування користувача та редірект на сторінку входу

function logoutUser() { // Функція для розлогування користувача
  sessionStorage.removeItem('user'); // Видалення з сесії

  window.location.replace('sign-in.html'); // Редірект на сторінку входу
}