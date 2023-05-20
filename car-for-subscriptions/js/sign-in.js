// Відправлення форми входу

document.addEventListener('DOMContentLoaded', function() { 
	const form = document.getElementById('form__sign-in');
	form.addEventListener('submit', formSend); 

	async function formSend(e){
		e.preventDefault(); 

		let email = document.getElementById('userInputEmail').value;
		let password = document.getElementById('userInputPassword').value;
		let url = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/users/' + email + '/' + password;
		let response = await fetch(url);
	  let responseJSON = await response.json();
		let status = responseJSON['id'];
		if (status) {
			sessionStorage.setItem('user', JSON.stringify(responseJSON));
			let subscriptionId = responseJSON['subscriptionId'];
			
			if(subscriptionId !== 0){
				 url = 'https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/' +subscriptionId;
				 response = await fetch(url);
				 responseJSON = await response.json();
				 status = responseJSON['status'];

				if(status==="Confirmed"){
					window.location.href = 'cabinet-active.html';
				}else{
					window.location.href = 'cabinet-expected.html';
				}
				
			}else{
				window.location.href = 'cabinet.html';
			}
			
		}else{
			const toastLiveExample = document.getElementById('liveToast')
			const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
			toastBootstrap.show()
		}
	};
})


// Редірект користувача в кабінет, при спробі повернутись до сторінки входу

if (sessionStorage.getItem('user') != null) {
	window.location.href = 'cabinet.html';
}