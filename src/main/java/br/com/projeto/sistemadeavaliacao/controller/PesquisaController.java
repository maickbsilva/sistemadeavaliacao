package br.com.projeto.sistemadeavaliacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
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
		model.addAttribute("pesq", pesquisaRepository.findById(id).get());
		model.addAttribute("perg", perguntaRepository.findAll());
		model.addAttribute("item", itemRepository.findAll());
		model.addAttribute("resposta", respostaRepository.findAll());

		return "pesquisa/listaResposta";

	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping(value = "novaPesquisa", method = RequestMethod.POST)
	public String novaPesquisa(Pesquisa pesquisa, String listaDocentes, RedirectAttributes attr) {
		
		pesquisaRepository.save(pesquisa);
		attr.addFlashAttribute("msgSucess", "O Codigo da Nova Pesquisa é:" + pesquisa.getId());
		return "redirect:cadastrar";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("listar")
	public String listaPesquisa(Model model) {
		model.addAttribute("pesq", pesquisaRepository.findAll());
		return "pesquisa/listaPesquisa";
	}

	@PostMapping("pesquisarid")
	public ModelAndView pesquisar(@RequestParam("idpesquisa") Long idpesquisa){
		ModelAndView modelAndView = new ModelAndView("pesquisa/listaPesquisa");
		modelAndView.addObject("listaResposta", respostaRepository.findById(idpesquisa));
		modelAndView.addObject("listaItem", itemRepository.findById(idpesquisa));
		modelAndView.addObject("listaResposta", new Resposta());
		return modelAndView;

	}

}
