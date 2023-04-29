// Можливість редагування імені

function editText() {
  // Отримую посилання на span
  var textElement = document.getElementById("username");
  // Отримую поточний текстовий вміст цього елемента
  var currentText = textElement.innerHTML;
  // Замінюю текст на форму для редагування
  textElement.innerHTML = '<input type="text" id="edit-input" value="' + currentText + '"><button onclick="saveText()">Зберегти</button>';
}

function saveText() {
  var inputElement = document.getElementById("edit-input");
  // Отримую введене користувачем значення
  var newText = inputElement.value;
  // Отримую посилання на HTML-елемент, що містить текст, який ми хочемо редагувати
  var textElement = inputElement.parentNode;
  // Замінюю форму для редагування на нове ім'я
  textElement.innerHTML = newText;
}


// Розгортання/згортання контенту (collapsible)

// #1
const itemWrapper1 = document.getElementById('item-wrapper-1');
const toggleBtn1 = document.getElementById('toggle-btn-1');
const activeBtn1 = document.getElementById('active-btn-1');

toggleBtn1.addEventListener('click', function() {
  if (itemWrapper1.classList.contains('open')) {
    // згорнути текст
    activeBtn1.classList.add('display-none');
    itemWrapper1.classList.remove('open');
    toggleBtn1.innerHTML = 'Показати більше';
  } else {
    // розгорнути текст
    activeBtn1.classList.remove('display-none');
    itemWrapper1.classList.add('open');
    toggleBtn1.innerHTML = 'Показати менше';
  }
});

// #2
const itemWrapper2 = document.getElementById('item-wrapper-2');
const toggleBtn2 = document.getElementById('toggle-btn-2');
const activeBtn2 = document.getElementById('active-btn-2');

toggleBtn2.addEventListener('click', function() {
  if (itemWrapper2.classList.contains('open')) {
    // згорнути текст
    activeBtn2.classList.add('display-none');
    itemWrapper2.classList.remove('open');
    toggleBtn2.innerHTML = 'Показати більше';
  } else {
    // розгорнути текст
    activeBtn2.classList.remove('display-none');
    itemWrapper2.classList.add('open');
    toggleBtn2.innerHTML = 'Показати менше';
  }
});

// #2
const itemWrapper3 = document.getElementById('item-wrapper-3');
const toggleBtn3 = document.getElementById('toggle-btn-3');
const activeBtn3 = document.getElementById('active-btn-3');

toggleBtn3.addEventListener('click', function() {
  if (itemWrapper3.classList.contains('open')) {
    // згорнути текст
    activeBtn3.classList.add('display-none');
    itemWrapper3.classList.remove('open');
    toggleBtn3.innerHTML = 'Показати більше';
  } else {
    // розгорнути текст
    activeBtn3.classList.remove('display-none');
    itemWrapper3.classList.add('open');
    toggleBtn3.innerHTML = 'Показати менше';
  }
});
