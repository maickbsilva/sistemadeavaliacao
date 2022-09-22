package br.com.projeto.sistemadeavaliacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;

@Controller

@RequestMapping("pergunta/")
public class PerguntaController {

	@Autowired
	private PerguntaRepository repository;

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("cadastrar")
	public String cadPergunta() {
		return "pergunta/cadPergunta";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping(value = "novaPergunta", method = RequestMethod.POST)
	public String novaPergunta(Pergunta pergunta) {
		repository.save(pergunta);
		return "redirect:cadastrar";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("listar")
	public String listaPergunta(Model model) {
		model.addAttribute("perg", repository.findAll());
		return "pergunta/listaPergunta";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("excluir")
	public String excluir(Long id) {
		repository.deleteById(id);
		return "redirect:listar";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("alterar")
	public String alterar(Long id, Model model) {
		Pergunta perg = repository.findById(id).get();
		model.addAttribute("p", perg);
		return "forward:cadastrar";
	}
}
