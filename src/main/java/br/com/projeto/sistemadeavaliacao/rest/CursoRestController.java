package br.com.projeto.sistemadeavaliacao.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.sistemadeavaliacao.model.Curso;
import br.com.projeto.sistemadeavaliacao.repository.CursoRepository;

@RestController
@RequestMapping("api/")
public class CursoRestController {

		@Autowired
		private CursoRepository repository;
		
		@RequestMapping(value = "listaCurso", method = RequestMethod.GET)
		public Iterable<Curso> getCurso(){
			
			return repository.findAll();
		}
}
