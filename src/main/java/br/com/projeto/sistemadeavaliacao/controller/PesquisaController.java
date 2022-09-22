package br.com.projeto.sistemadeavaliacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.CursoRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.UsuarioRepository;

@Controller

@RequestMapping("pesquisa/")
public class PesquisaController {

	@Autowired
	private PesquisaRepository pesquisaRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private RespostaRepository respostaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("cadastrar")
	public String cadPesquisa(Model model) {
		model.addAttribute("cursos", cursoRepository.findAll());
		model.addAttribute("respostas", respostaRepository.findAll());
		model.addAttribute("usuario", usuarioRepository.findAll());
		return "pesquisa/cadPesquisa";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping(value = "novaPesquisa", method = RequestMethod.POST)
	public String novaPesquisa(Pesquisa pesquisa) {
		pesquisaRepository.save(pesquisa);
		return "redirect:cadastrar";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("listar")
	public String listaPesquisa(Model model) {
		model.addAttribute("pesq", pesquisaRepository.findAll());
		return "pesquisa/listaPesquisa";
	}

	@RequestMapping("buscar")
	public String buscaPesquisa(Long id, Model model) {
		model.addAttribute("pesq", pesquisaRepository.findById(id).get());
		return "pesquisa/listaPesquisa";
	}
}
