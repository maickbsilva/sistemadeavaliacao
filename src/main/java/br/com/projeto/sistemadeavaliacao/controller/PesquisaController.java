package br.com.projeto.sistemadeavaliacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;

@Controller
@RequestMapping("pesquisa/")
public class PesquisaController {
    @Autowired
    private PesquisaRepository pesquisaRepository;

    @RequestMapping("cadastrar")
    public String cadPesquisa(){
        return "pesquisa/cadPesquisa";
    }

    @RequestMapping(value = "novaPesquisa", method = RequestMethod.POST)
    public String novaPesquisa(Pesquisa pesquisa){
        pesquisaRepository.save(pesquisa);
        return "redirect:cadastrar";
    }
}
