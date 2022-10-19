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

		// se existir um idpesquisa salva ele nas perguntas que foram associadas
		if (idpesquisa != null) {
			List<Pesquisa> listaPesquisa = new ArrayList<>();

			// pega cada posicao do idpesquisa
			String[] quebraIdPesquisa = idpesquisa.split(",");

			// seta cada id da pesquisa em uma nova pesquisa e add na lista
			for (int i = 0; i < quebraIdPesquisa.length; i++) {
				Pesquisa pesq = new Pesquisa();
				String posicaoPesq = quebraIdPesquisa[i];
				pesq.setId(Long.parseLong(posicaoPesq));
				listaPesquisa.add(pesq);
			}

			/*seta a lista gerada pelo FOR dentro do atributo "listaPesquisa" da pergunta, 
			isso preenche a tabela relacional pergunta_lista_pesquisa*/
			pergunta.setListaPesquisa(listaPesquisa);
			repository.save(pergunta);
		}

		// se nao existir idpesquisa, passa somente a pergunta
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