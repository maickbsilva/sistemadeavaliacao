package br.com.projeto.sistemadeavaliacao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.model.Docente;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.CursoRepository;
import br.com.projeto.sistemadeavaliacao.repository.DocenteRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;

@Controller
@RequestMapping("pesquisa/")
public class PesquisaController {
    
    @Autowired
    private PesquisaRepository pesquisaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @RequestMapping("cadastrar")
    public String cadPesquisa(Model model){
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("docentes", docenteRepository.findAll());
        model.addAttribute("respostas", respostaRepository.findAll());
        return "pesquisa/cadPesquisa";
    }

    @RequestMapping(value = "novaPesquisa", method = RequestMethod.POST)
    public String novaPesquisa(Pesquisa pesquisa){
        pesquisaRepository.save(pesquisa);
        return "redirect:cadastrar";
    }
    @RequestMapping("listar")
    public String listaPesquisa(Model model){
        model.addAttribute("pesq", pesquisaRepository.findAll());
        return "pesquisa/listaPesquisa";
    }
}
