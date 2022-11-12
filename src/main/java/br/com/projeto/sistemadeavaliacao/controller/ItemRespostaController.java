package br.com.projeto.sistemadeavaliacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.DocenteAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;

@Controller
@RequestMapping("itemresposta/")
public class ItemRespostaController {

	@Autowired
	private ItemRespostaRepository itresp;

	@DiretorAnnotation
	@DocenteAnnotation
	@SecretariaAnnotation
	@RequestMapping("cadastrar")
	public String cadItem() {
		return "itemresposta/cadItem";
	}

	@RequestMapping(value = "buscaResp", method = RequestMethod.GET)
	public String iResp(String busca, Model model, Long id){
		model.addAttribute("buscar", itresp.findById(id));
		return "pesquisa/listaResposta";

	}

}
