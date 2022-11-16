
// function logar() {
//   var login = document.getElementById('login').value;
//   var senha = document.getElementById('senha').value;
  

//   if(login == "admin" && senha == "admin") {
//     alert('Sucesso, bem vindo de volta ');
//   }else{
//     alert('Usuario ou senha incorretos');
//   }showAlert


  
//}
$('button').click(function(){
    $('.alert').removeClass("hide");
    $('.alert').addClass("show");
    $('.alert').addClass("showAlert");
    setTimeout(function(){
      $('.alert').addClass("hide");
      $('.alert').removeClass("show");
    },3000); // hide alert automatically after 5sec
  });
  $('close-btn').click(function(){
    $('.alert').addClass("hide");
    $('.alert').removeClass("show");
  });