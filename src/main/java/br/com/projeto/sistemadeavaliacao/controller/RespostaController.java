package br.com.projeto.sistemadeavaliacao.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.projeto.sistemadeavaliacao.model.Resposta;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;

@Controller
@RequestMapping("resposta/")
public class RespostaController {
    
    @Autowired
    private ItemRespostaRepository itemRespostaRepository;

    @Autowired
    private PesquisaRepository pesquisaRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @RequestMapping("cadastrar")
    public String cadResposta(){
        return "resposta/cadResposta";
    }

    @RequestMapping("formulario")
    public String novaResposta(Resposta resposta, Model model){
        Date now = new Date(System.currentTimeMillis());
        resposta.setDataRealizacao(now);
        respostaRepository.save(resposta);  
        return "redirect:cadastrar";
    }

    /**
     *     	@RequestMapping("cadCurso")
	public String acessoSec(Model model) {
		model.addAttribute("tipos",repositoryTC.findAll());
		model.addAttribute("periodocurso", repositoryPC.findAll());
		return "cadCurso";
	}
     */
}
