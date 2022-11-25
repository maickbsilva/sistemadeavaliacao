package br.com.projeto.sistemadeavaliacao.controller;

import br.com.projeto.sistemadeavaliacao.model.*;
import br.com.projeto.sistemadeavaliacao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("itemResposta/")
public class ItemRespostaController {

    @Autowired
    private ItemRespostaRepository itemRespostaRepository;
    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private JustificativaItemRespostaRepository justificativaItemRespostaRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private PesquisaRepository pesquisaRepository;

    @RequestMapping("justificativa")
    public String justificativaItem(ItemResposta itemResposta, Model model) {
		Long id = itemResposta.getId();
        model.addAttribute("itemResposta", id);
        Pergunta idPerg = itemRespostaRepository.findPergunta(id);
        model.addAttribute("item", itemRespostaRepository.findById(id).get());
        model.addAttribute("pergunta", perguntaRepository.findById(idPerg.getId()).get());
        return "justificativa/justificativaItem";
    }


    @PostMapping("novaJustiItem")
    public String novaJustificativa(JustificativaItemResposta justificativaItemResposta, HttpServletRequest request) {

        //pega a data de hoje e insere ela na justificativa
        Date data = new Date();
        justificativaItemResposta.setData(data);

        //pega o usuario da sessao e insere ele na justificativa
        Usuario u = (Usuario) request.getSession().getAttribute("docenciaLogado");
        justificativaItemResposta.setUsuario(u);

        justificativaItemRespostaRepository.save(justificativaItemResposta);

        Long idItem = justificativaItemResposta.getItemResposta().getId();
        Resposta r = (Resposta) itemRespostaRepository.buscaResposta(idItem);
        Pesquisa p = respostaRepository.buscaPesquisa(r.getId());
        p.setJustificativa(false);
        pesquisaRepository.save(p);

        return "redirect:/";
    }

    @RequestMapping("solicitaJustificativa")
    public String solicitaJustificativa(Long id, HttpServletRequest request) {
        Resposta r = (Resposta) itemRespostaRepository.buscaResposta(id);
        Pesquisa p = respostaRepository.buscaPesquisa(r.getId());
        p.setJustificativa(true);
        pesquisaRepository.save(p);
        System.out.println("pesquisa: " + p.getId() + ", status justificativa: " + p.isJustificativa());

        //redireciona para a pag anterior
        String referer = request.getHeader("Referer");

        return "redirect:"+referer;
    }

}
