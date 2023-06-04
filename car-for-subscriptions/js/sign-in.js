// Відправлення форми входу

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('form__sign-in');
    form.addEventListener('submit', formSend);

    async function formSend(e) {
        e.preventDefault();

        let email = document.getElementById('userInputEmail').value;
        let password = document.getElementById('userInputPassword').value;
        let url = 'https://carfinity-api.lm.r.appspot.com/api/v1/users/' + email + '/' + password;
        let response = await fetch(url);
        let responseJSON = await response.json();
        let status = responseJSON['id'];
        if (status) {
            sessionStorage.setItem('user', JSON.stringify(responseJSON));
            window.location.href = 'cabinet.html';

        } else if (response.status === 404) {
            const toastLiveExample = document.getElementById('liveToast')
            const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
            toastBootstrap.show()
            const bodyText = document.getElementById('alertMessage')
            bodyText.innerHTML = "Невірний логін або пароль"
        } else if (response.status === 403) {
            const toastLiveExample = document.getElementById('liveToast')
            const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
            toastBootstrap.show()
            const bodyText = document.getElementById('alertMessage')
            bodyText.innerHTML = "Ваш аккаунт було заблоковано!"
        } else {
            const toastLiveExample = document.getElementById('liveToast')
            const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
            toastBootstrap.show()
            const bodyText = document.getElementById('alertMessage')
            bodyText.innerHTML = "Помилка серверу! Спробуйте пізніше"
        }
    };
})


// Редірект користувача в кабінет, при спробі повернутись до сторінки входу

if (sessionStorage.getItem('user') != null) {
    window.location.href = 'cabinet.html';
}