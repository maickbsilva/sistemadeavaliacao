package br.com.projeto.sistemadeavaliacao.controller;

import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.CursoRepository;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.UsuarioRepository;

import java.util.List;

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

	@Autowired
	private ItemRespostaRepository itemRespostaRepository;


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
    public String buscaPesquisa(Long id, Model model){
        model.addAttribute("pesq", pesquisaRepository.findById(id).get());
        return "pesquisa/listaPesquisa";
    }
    
    @RequestMapping("listarResposta")
    public String listarRespota(Long id, Model model) {
    	model.addAttribute("pesq",pesquisaRepository.findById(id).get());
    	model.addAttribute("perg", perguntaRepository.findAll());
		model.addAttribute("item", itemRepository.findAll());
    	model.addAttribute("resposta",respostaRepository.findAll());
    	
		return "pesquisa/listaResposta";
    	
    }
    
	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping(value="novaPesquisa", method = RequestMethod.POST)
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

	@RequestMapping("media")
	public String mediaeComentario(Long id, Model model) {

		List<Pergunta> perguntas = perguntaRepository.filtroPerguntas(id);
		Pesquisa pesquisa = pesquisaRepository.filtroExclusao(id);

		//se existir pergunta a se excluida, exclui da lista
		if (pesquisa != null) {
			pesquisa.getPerguntaExclusao().forEach(iten -> {
				perguntas.remove(iten);
			});
		}

		model.addAttribute("pesq", pesquisaRepository.findById(id).get());
		model.addAttribute("perg", perguntas);
		model.addAttribute("item", itemRespostaRepository.findAll());

		//coletar media de satisfacao - esta no item resposta

		return "pesquisa/mediaecomentario";
	}

}
