// Динамічний header в залежності від того, чи користувач залогований

  const headerEl = document.querySelector('.header__last-item');
  const userStr = sessionStorage.getItem('user');
  if (userStr) {
    // Якщо користувач залогований, виводимо кнопки "Мій кабінет/Вийти"
    const user = JSON.parse(userStr);
    headerEl.innerHTML = `
    <li class="nav-item">
      <a class="nav-link active header__sign-in" aria-current="page" href="cabinet-inactive.html">Мій кабінет</a><span class="header__slash">/</span><a class="header__sign-up" href="#" onclick="logoutUser()">Вийти</a>
    </li>
    `;
    const logoutBtn = headerEl.querySelector('#logout-btn');
    if (logoutBtn) {
      logoutBtn.addEventListener('click', logoutUser);
    }
  } else {
    // Якщо користувач не залогований, виводимо кнопки "Увійти/Зареєструватись"
    headerEl.innerHTML = `
    <li class="nav-item">
      <a class="nav-link active header__sign-in" aria-current="page" href="sign-in.html">Увійти</a><span class="header__slash">/</span><a class="header__sign-up" href="sign-up.html">Зареєструватись</a>
    </li>
    `;
  }

// Розлогування користувача та редірект на сторінку входу

function logoutUser() { // Функція для розлогування користувача
  sessionStorage.removeItem('user'); // Видалення з сесії

  window.location.replace('sign-in.html'); // Редірект на сторінку входу
}