package br.com.projeto.sistemadeavaliacao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;

@Controller

@RequestMapping("pergunta/")
public class PerguntaController {

	@Autowired
	private PerguntaRepository repository;

	@Autowired
	private PesquisaRepository pesquisaRepository;

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("cadastrar")
	public String cadPergunta(Model model) {
		model.addAttribute("pesq", pesquisaRepository.findAll());
		return "pergunta/cadPergunta";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping(value = "novaPergunta", method = RequestMethod.POST)
	public String novaPergunta(Pergunta pergunta, String idpesquisa) {

		List<Pesquisa> listaPesquisa = new ArrayList<>();

		//pega cada posicao do idpesquisa
		String[] quebraIdPesquisa = idpesquisa.split(",");

		//enquanto for menor do que o tamanho do array quebraIdPesquisa
		for (int i = 0; i < quebraIdPesquisa.length; i++) {
			//nova pesquisa
			Pesquisa pesq = new Pesquisa();
			//pega a posicao da quebraIdPesquisa
			String posicaoPesq = quebraIdPesquisa[i];
			//insere a posicao no id da nova pesquisa
			pesq.setId(Long.parseLong(posicaoPesq));
			//insere a pesquisa na listaPesquisa
			listaPesquisa.add(pesq);
		}
		//associa cada pergunta com a lista de pesquisas
		pergunta.setListaPesquisa(listaPesquisa);
		
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
