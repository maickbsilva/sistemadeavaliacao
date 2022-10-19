let msg_alerta = '<div class="alert-box">'
+'<h1>Aviso</h1>'
+'<p>Protocolo incorreto, favor informar um valido.</p>'
+'<input style="padding:5px 10px;" type="button" value="OK" onclick="this.parentNode.outerHTML=\'\';window.location.href=\'consulta.php\';" />'
+'</div>';

document.write(msg_alerta);