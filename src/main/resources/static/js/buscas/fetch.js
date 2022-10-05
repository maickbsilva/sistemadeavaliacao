const ul = document.getElementById('user')
const url = 'http://10.92.198.39:8080/api/listaUsuario'



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
    })

function createNode(element) {
    return document.createElement(element)
}

function append(parent, el) {
    return parent.appendChild(el);
}

// pegar as informações
let fetchData = () => {
    let url = `http://10.92.198.39:8080/api/listaUsuario `

    fetch(url)
        .then(response => response.json())
        .then(fetchData => {
            console.log(fetchData)
        })
}
fetchData()


