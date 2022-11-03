package br.com.projeto.sistemadeavaliacao.controller;

import java.util.ArrayList;
import java.util.List;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.model.Resposta;
import br.com.projeto.sistemadeavaliacao.repository.CursoRepository;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
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
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ItemRespostaRepository itemRepository;

	@Autowired
	private PerguntaRepository perguntaRepository;

	@Autowired
	private RespostaRepository respostaRepository;

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("cadastrar")
	public String cadPesquisa(Model model) {
		model.addAttribute("cursos", cursoRepository.findAll());
		model.addAttribute("respostas", respostaRepository.findAll());
		model.addAttribute("usuario", usuarioRepository.BuscarDocentes());
		model.addAttribute("pergunta", perguntaRepository.perguntasGerais());
		return "pesquisa/cadPesquisa";
	}

	@RequestMapping("buscar")
	public String buscaPesquisa(Long id, Model model) {
		model.addAttribute("pesq", pesquisaRepository.findById(id).get());
		return "pesquisa/listaPesquisa";
	}

	@RequestMapping("listarResposta")
	public String listarRespota(Long id, Model model) {

		// chama a criptografia
		BasicTextEncryptor encryptor = new BasicTextEncryptor();

		//String comentarioCrypt = encryptor.decrypt(comentarioGeral);

		Resposta comentarioGeral = respostaRepository.comentarioGeral();
		String quebra[] = comentarioGeral.toString().split(",");

		for (int i = 0; i < quebra.length; i++) {
			String coment = quebra[i];
			String descrip = encryptor.decrypt(coment);
			//salva isso dentro de uma lista para chamar a lista dentro da model
		}

	
		model.addAttribute("pesq", pesquisaRepository.findById(id).get());
		model.addAttribute("perg", perguntaRepository.findAll());
		model.addAttribute("item", itemRepository.findAll());
		model.addAttribute("resposta", respostaRepository.findAll());

		return "pesquisa/listaResposta";

	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping(value = "novaPesquisa", method = RequestMethod.POST)
	public String novaPesquisa(Pesquisa pesquisa, String listaDocentes) {
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

}
