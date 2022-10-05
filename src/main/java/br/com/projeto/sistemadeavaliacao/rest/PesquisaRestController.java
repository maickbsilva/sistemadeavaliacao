package br.com.projeto.sistemadeavaliacao.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;

@RestController
@RequestMapping("")
public class PesquisaRestController {

	@Autowired
	private PesquisaRepository repository;
	
	@RequestMapping(value = "pesquisar", method = RequestMethod.GET)
	public Iterable<Pesquisa> getPesquisa(){
		return repository.findAll();
	}
}
