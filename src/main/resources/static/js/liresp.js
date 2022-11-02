const INPUT_BUSCA = document.getElementById('demo')
const TABLE_RESP = document.getElementById('list')

INPUT_BUSCA.addEventListener('keyup', () => {
  let respSelected = INPUT_BUSCA.value;

  let linhas = TABLE_RESP.getElementsByTagName('tr');
  console.log(linhas)
  for (let posicao in linhas) {
    if (true === isNaN(posicao)) {
      continue;
    }
    let conteudoLinha = linhas[posicao].innerHTML;
    if (true === conteudoLinha.includes(respSelected)) {
      linhas[posicao].style.display = '';
    } else {
      linhas[posicao].style.display = 'none';
    }
  }
})

/*
function search_id() {
  let input = document.getElementById('searchbar').value
  input=input.toLowerCase();
  let x = document.getElementsByClassName('psp');

  for (i = 0; i < x.length; i++) {
      if (!x[i].innerHTML.toLowerCase().includes(input)) {
          x[i].style.display="none";

      }
      else {
          x[i].style.display="list-item";
      }
  }
}
----------------------------------------------------------
$(document).ready(function(){
    escolas.forEach(function(item){
        $('select.lista-escola').append('<option>' + item + '</option>');
    });

    $('select.lista-escola').change(function(){
        var valueSelected = this.value;
        if(valueSelected=="UFRRJ"){
            alunosRural.forEach(function(item){
                $('select.lista-aluno').append('<option>' + item + '</option>');
            });
        }
    });
});



*/
//onChange requerid back pedindo lista dql resposta 