
const ul = document.getElementById('user')
const url = 'http://10.92.198.39:8080/api/listaUsuario'


// substituir o fazGet pelo fetchData
let fetchData = () => {
    let url = `http://10.92.198.39:8080/api/listaUsuario `

    fetch(url)
        .then(response => response.json())
        .then(fetchData => {
            console.log(fetchData)
        })
}
main()


 function fazGet(url){
    let request = new XMLHttpRequest();
    request.open("GET", url, false);
    request.send();
    return request.responseText
} 

function criaLinha(user){
    linha = document.createElement("tr")
    tdId = document.createElement("td")
    tdNome = document.createElement("td")
    tdId.innerHTML =  `${user.userId}`
    tdNome.innerHTML =  `${user.nome}`
    
    linha.appendChild(tdId)
    linha.appendChild(tdNome)
    return linha;
}


function main(){
    let data = fazGet("http://10.92.198.39:8080/api/listaUsuario")
    let user = JSON.parse(data);
    let tabela = document.getElementById("tba")
    user.forEach(element => {
        let linha = criaLinha(element);
        tabela.appendChild(linha);
    });
}


/*
fetch(url)
    .then((resp) => resp.json())
    .then(data => {
        let usuarios = data;
        return usuarios.map((user) => {


            let span = createNode('span')
            span.innerHTML = `${user.id} ${user.nome}`

           
        })

    })

    .catch((error) => {
        console.log(error);
    })*/

/*function createNode(element) {
    return document.createElement(element)
}

function append(parent, el) {
    return parent.appendChild(el);
}
*/




