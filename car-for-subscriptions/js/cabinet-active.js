if (sessionStorage.getItem('user') != null) {
    let user = sessionStorage.getItem('user');
    let userJson = JSON.parse(user);
    let subId = userJson['subscriptionId'];
    if (subId == 0) {
    window.location.href = 'http://localhost:7886/cabinet-inactive.html';
    }else if(subId > 0){
        if (sessionStorage.getItem('subscription') == null) {
            let getResponse = await fetch('https://circular-ally-383113.lm.r.appspot.com/api/v1/subscriptions/'+subId) //відправка технологією AJAX, за допомогою fetch
            .then(response => response.json())
            .then(json => sessionStorage.setItem('subscription', JSON.stringify(json)));

            window.location.href = 'http://localhost:7886/cabinet-active.html';
        }
    }

}else{
    window.location.href = 'http://localhost:7886/sign-in.html';

}