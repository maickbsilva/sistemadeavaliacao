package br.com.projeto.sistemadeavaliacao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.DocenteAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;

@Controller
@RequestMapping("itemresposta/")
public class ItemRespostaController {

	@DiretorAnnotation
	@DocenteAnnotation
	@SecretariaAnnotation
	@RequestMapping("cadastrar")
	public String cadItem() {
		return "itemresposta/cadItem";
	}

}
