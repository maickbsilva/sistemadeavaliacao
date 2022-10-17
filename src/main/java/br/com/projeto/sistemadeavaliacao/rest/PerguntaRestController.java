package br.com.projeto.sistemadeavaliacao.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;

public class PerguntaRestController {

@RestController
@RequestMapping("api/")
public class CursoRestController {

		@Autowired
		private PerguntaRepository perguntaRepository;
		
		@RequestMapping(value = "listaPergunta", method = RequestMethod.GET)
		public Iterable<Pergunta> getPergunta(){
			
			return perguntaRepository.findAll();
		}
}
}
