var btnLogin = document.getElementById('do-login');
/* Section Change */
var fundoSection = document.getElementById('fundo');

var forgot = document.getElementById('do-forgot')
var idforgot = document.getElementById('forgot');

var username = document.getElementById('username');
/* New Listener */
btnLogin.addEventListener('click',function () {
  fundoSection.innerHTML = '<p> Estamos felizes em vê-lo novamente </p><br><a href="index.html">Voltar</a><h1>' +username.value+ '</h1>';
})
/* New Listener */
forgot.addEventListener('click',function () {
  fundoSection.innerHTML = '<p> Esqueceu a senha ? </p><br><a href="index.html">Voltar</a><h1>' +username.value+ '</h1>';
})