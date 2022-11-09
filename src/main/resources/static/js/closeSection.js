function abrirTudo() {
    const closedAndOpen = document.getElementById('hiddenAndShow')
    const btnExb = document.getElementById('btnExibir')

    if (closedAndOpen.style.display === 'open') {
        closedAndOpen.style.display = "inline"
        btnExb.innerHTML = "Fechar Todas Respostas"
    } else {
        closedAndOpen.style.display = "none"
        
        btnExb.innerHTML = "Abrir Todas Respostas"
    }
}