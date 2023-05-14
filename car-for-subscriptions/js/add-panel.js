


async function addCar(){
    let newCar = {
        "name": document.getElementById('name').value,
        "model": document.getElementById('model').value,
        "brand": document.getElementById('brand').value,
        "year": document.getElementById('year').value,
        "color": document.getElementById('color').value,
        "price": document.getElementById('price').value,
        "fuelType": document.getElementById('fuelType').value,
        "chassisNumber": document.getElementById('chassisNumber').value,
        "regNumber":document.getElementById('regNumber').value,
        "regDate": document.getElementById('regDate').value,
        "mileage": document.getElementById('mileage').value,
        "lastServiceDate":document.getElementById('lastServiceDate').value,
        "statusId": "1",
        "image":document.getElementById('image').value 
     
    };
    

    const response = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/cars', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(newCar)
			});
			let responseJSON = await response.json();
			let status = responseJSON['status'];
            if (status === 'success') {
                let message = responseJSON['message'];
				alert(message);
			}else{
				let error = responseJSON['errorMessage'];
				alert(error);
			}
}