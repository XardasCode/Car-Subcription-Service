let user = sessionStorage.getItem('user');
let userJson = JSON.parse(user);
const subId = userJson['subscriptionId'];

window.addEventListener("DOMContentLoaded", (event) => {
        getSubscription(subId);
});

async function getSubscription(id){
         let url = 'https://carfinity-api.lm.r.appspot.com/api/v1/subscriptions/' + id;
		let response = await fetch(url);
	    let responseJSON = await response.json();
    document.getElementById('month-price').textContent = responseJSON['monthPrice'];
}

 function getApprovingURL(){
    let price = document.getElementById('month-price').textContent;
   
    let paymentJson = {
        "price": price,
        "currency": "USD",
        "method": "paypal",
        "intent": "sale",
        "description" : "Payment for car subscription"
    };

   fetch(`https://carfinity-api.lm.r.appspot.com/api/v1/pay/paypal/${subId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(paymentJson)
    }).then(response => response.json())
    .then(result => {
        if (result['status'] === 'success') {
            window.location.href = result['message'];
        }else{
            const toastLiveExample = document.getElementById('liveToast')
            const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
            toastBootstrap.show()
            const bodyText = document.getElementById('alertMessage')
            bodyText.innerHTML = "Не вдалося створити оплату!"
        }
      console.log(result);
    })
}
