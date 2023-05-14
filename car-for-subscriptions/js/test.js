const textWrapper = document.querySelector('.text-wrapper');
// const text = document.querySelector('.text');
const toggleBtn = document.querySelector('.toggle-btn');

toggleBtn.addEventListener('click', function() {
  if (textWrapper.classList.contains('open')) {
    // згорнути текст
    textWrapper.classList.remove('open');
    toggleBtn.innerHTML = 'Розгорнути';
  } else {
    // розгорнути текст
    textWrapper.classList.add('open');
    toggleBtn.innerHTML = 'Згорнути';
  }
});






 function searchCars() {
    const make = document.getElementById("make").value;
    const model = document.getElementById("model").value;
    const year = parseInt(document.getElementById("year").value);
    
    const filteredCars = carCatalog.filter(car => {
      return (!make || car.make === make) && 
             (!model || car.model === model) && 
             (!year || car.year === year);
    });
    
    console.log(filteredCars);
  }
