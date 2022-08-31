package br.com.projeto.sistemadeavaliacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;

@Controller
@RequestMapping("itemresposta/")
public class ItemRespostaController {

    @Autowired
    private ItemRespostaRepository itemRespostaRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @RequestMapping("cadastrar")
    public String cadItem(){
        return "itemresposta/cadItem";
    }


}
