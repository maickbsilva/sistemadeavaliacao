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

    @Autowired
    private JustificativaRespostaRepository justificativaRespostaRepository;

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

        return "telainicial/telaInicialDocencia";
    }

    @RequestMapping("solicitaJustificativa")
    public String solicitaJustificativa(Long id, HttpServletRequest request) {
        Resposta r = (Resposta) itemRespostaRepository.buscaResposta(id);
        Pesquisa p = respostaRepository.buscaPesquisa(r.getId());
        p.setJustificativa(true);
        pesquisaRepository.save(p);
        System.out.println("pesquisa: " + p.getId() + ", status justificativa item: " + p.isJustificativa());

        //redireciona para a pag anterior
        String referer = request.getHeader("Referer");

        return "redirect:"+referer;
    }

    @RequestMapping("justificativaResposta")
    public String justificativaResposta(Resposta resposta, Model model) {
        Long id = resposta.getId();
        model.addAttribute("idResposta", id);
        model.addAttribute("resposta", respostaRepository.findById(id).get());
        return "justificativa/justificativaResposta";
    }

    @PostMapping("novaJustiResp")
    public String novaJustificativaResposta(JustificativaResposta justificativaResposta, HttpServletRequest request) {

        //pega a data de hoje e insere ela na justificativa
        Date data = new Date();
        justificativaResposta.setData(data);

        //pega o usuario da sessao e insere ele na justificativa
        Usuario u = (Usuario) request.getSession().getAttribute("docenciaLogado");
        justificativaResposta.setUsuario(u);

        justificativaRespostaRepository.save(justificativaResposta);

        Long idResp = justificativaResposta.getResposta().getId();
        Optional<Resposta> r = respostaRepository.findById(idResp);
        Pesquisa p = respostaRepository.buscaPesquisa(r.get().getId());
        p.setJustificativa(false);
        pesquisaRepository.save(p);

        return "telainicial/telaInicialDocencia";
    }

    @RequestMapping("solicitaJustificativaResposta")
    public String solicitaJustificativaResposta(Long id, HttpServletRequest request) {
        Pesquisa p = respostaRepository.buscaPesquisa(id);
        p.setJustificativa(true);
        pesquisaRepository.save(p);
        System.out.println("pesquisa: " + p.getId() + ", status justificativa resposta: " + p.isJustificativa());

        //redireciona para a pag anterior
        String referer = request.getHeader("Referer");

        return "redirect:"+referer;
    }

}
