let numToShow = 4;
let items = document.querySelectorAll('.list li');
for (let i = 0; i < numToShow; i++) {
  items[i].style.display = "block";
}

document.getElementById("show-more").addEventListener("click", function() {
  numToShow += 4;
  for (let i = 0; i < items.length; i++) {
    if (i < numToShow) {
      items[i].style.display = "block";
    }
  }
});

document.getElementById("show-less").addEventListener("click", function() {
  numToShow -= 4;
  if (numToShow < 0) {
    numToShow = 0;
  }
  for (let i = 0; i < items.length; i++) {
    if (i < numToShow) {
      items[i].style.display = "block";
    } else {
      items[i].style.display = "none";
    }
  }
});