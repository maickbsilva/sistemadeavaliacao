package br.com.projeto.sistemadeavaliacao.controller;

import java.sql.Date;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.model.Resposta;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
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

    @Autowired
    private PerguntaRepository perguntaRepository;

    @RequestMapping("formulario")
    public String formulario(Model model, Pergunta pergunta){
        model.addAttribute("perg", perguntaRepository.findAll());
        model.addAttribute("item", itemRespostaRepository.findAll());
        return "resposta/formulario";
    }

    @RequestMapping(value = "novoFormulario", method = RequestMethod.POST)
    public String novaResposta(Resposta resposta, ItemResposta itemResposta, Model model){
        Date now = new Date(System.currentTimeMillis());
        resposta.setDataRealizacao(now);
        respostaRepository.save(resposta);
        itemRespostaRepository.save(itemResposta);
        return "redirect:formulario";
    }


    /**
     *     @RequestMapping("listar")
    public String listaPergunta(Model model){
        model.addAttribute("perg", repository.findAll());
        return "pergunta/listaPergunta";
    }
     */
}
