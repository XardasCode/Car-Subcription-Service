


// Розлогування користувача та редірект на сторінку входу

function logoutUser() { 
  sessionStorage.removeItem('user'); // Видалення з сесії

  window.location.replace('sign-in.html'); // Редірект на сторінку входу
}