
let subId = "";
window.addEventListener("DOMContentLoaded", () => {
    let user = sessionStorage.getItem('user');
    let userJson = JSON.parse(user);
    subId = userJson['subscriptionId'];
    getSubscription(subId);
});

async function getSubscription(id){
   let url = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/' + id;
   let responseSub = await fetch(url);
   let subscriptionJSON  = await responseSub.json();
   url = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/cars/' + subscriptionJSON['carId'];
   let responseCar = await fetch(url);
   let carJSON = await responseCar.json();
   console.log(carJSON)
   document.getElementById('brand').textContent = carJSON.brand;
   document.getElementById('name').textContent = carJSON.name+" "+carJSON.model;
   document.getElementById('chassisNumber').textContent = carJSON.chassisNumber;
   document.getElementById('fuelType').textContent = carJSON.fuelType;
   document.getElementById('color').textContent = carJSON.color;
   document.getElementById('regNumber').textContent = carJSON.regNumber;
   document.getElementById('year').textContent = carJSON.year;
   document.getElementById('regDate').textContent = carJSON.regDate;
   document.getElementById('mileage').textContent = carJSON.mileage;
   document.getElementById('lastServiceDate').textContent = carJSON.lastServiceDate;
   document.getElementById('lastPayDate').textContent = subscriptionJSON.lastPayDate.substring(0, subscriptionJSON.lastPayDate.lastIndexOf("."));

}

async function downloadPDF() {
    if(subId){
       
        //let host = "http://localhost:8081";
        let host = "https://circular-ally-383113.lm.r.appspot.com";

        fetch(`${host}/api/v1/subscriptions/${subId}/generate-pdf`, {
            method: 'GET',
          })
            .then(response => response.blob())
            .then(blob => {
              // Create a temporary URL for the blob
              const url = URL.createObjectURL(blob);
          
              // Create a link element and set its attributes
              const link = document.createElement('a');
              link.href = url;
              link.download = 'report.pdf';
          
              // Programmatically click the link to start the download
              link.click();
          
              // Clean up the temporary URL
              URL.revokeObjectURL(url);
            })
            .catch(error => {
              // Handle any errors
              console.error('Error:', error);
            });
    }
  
  
}


function Close(){
    window.location.href = 'cabinet.html'
}
