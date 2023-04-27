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