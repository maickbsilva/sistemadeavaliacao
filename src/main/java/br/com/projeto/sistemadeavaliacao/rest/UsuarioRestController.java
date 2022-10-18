package br.com.projeto.sistemadeavaliacao.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.projeto.sistemadeavaliacao.model.Usuario;
import br.com.projeto.sistemadeavaliacao.repository.UsuarioRepository;

@RestController
@RequestMapping("api/")
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@RequestMapping(value = "listaUsuario", method = RequestMethod.GET)
	public Iterable<Usuario> getUsuario(){	
		return usuarioRepository.findAll();
	}
	
}
