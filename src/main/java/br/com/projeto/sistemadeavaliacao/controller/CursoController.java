package br.com.projeto.sistemadeavaliacao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.model.Curso;
import br.com.projeto.sistemadeavaliacao.model.TipoCurso;
import br.com.projeto.sistemadeavaliacao.repository.CursoRepository;




@Controller
public class CursoController {
	
	@Autowired
	private CursoRepository cursoRepository;

	
	@RequestMapping(value = "curso", method = RequestMethod.GET)
	private String formCurso(Model model) {	
		
		model.addAttribute("cursoR", cursoRepository.findAll());
		return "curso/curso";
	}
	
	//salvar 
	@RequestMapping(value = "salvarCurso", method = RequestMethod.POST)
	 public String salvar(Curso cs) {
		 cursoRepository.save(cs);
		 return "redirect:curso";
	 }
	
	//Listar tipo de curso ;
	@RequestMapping("listaCurso/{page}")
	public String list (Model model, @PathVariable("page")int page) {
		
		//criando uma pageble
		
		PageRequest pageble = PageRequest.of(page-1,10, Sort.by(Sort.Direction.ASC,"descCurso"));
		
		Page<Curso> pagina = cursoRepository.findAll(pageble);
		model.addAttribute("cursoC", pagina.getContent());
		int totalPaginas = pagina.getTotalPages();
		//vetor da lista 
		List<Integer> numPaginas = new ArrayList<Integer>();
		
		//prechendo a lista 
		
		for (int i = 1 ; i <= totalPaginas; i++) {
			numPaginas.add(i);
		}
		model.addAttribute("numPagina",numPaginas);
		model.addAttribute("totalPages",totalPaginas);
		model.addAttribute("pagAtual",page);
		return "curso/listaCurso";
	}
	
	//--alterando--//
	@RequestMapping("alteraCurso")
	public String alterare(Long id, Model model) {
		Curso curso = cursoRepository.findById(id).get();
		model.addAttribute("cs", curso);
		return "forward:curso";
	}
	
	//--exluindo--//
	@RequestMapping("exclueCurso")
	public String excluir(Long id) {
		// excluir a reserva pelo ID
		Curso  curso = cursoRepository.findById(id).get();
		cursoRepository.delete(curso);
		

		
		
		return "redirect:listaCurso/1";
	}
	//--Buscando--//
	//verificar não funciona
	@RequestMapping(value = "buscarCurso", method = RequestMethod.GET)
	public String buscar(String buscar, Model model) {
		model.addAttribute("curso", cursoRepository.buscarCurso(buscar));

		return "curso/listaBuscarCurso";
	}
	//
	
	
}
