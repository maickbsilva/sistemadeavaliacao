package br.com.projeto.sistemadeavaliacao.controller;

import br.com.projeto.sistemadeavaliacao.model.*;
import br.com.projeto.sistemadeavaliacao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("itemResposta/")
public class ItemRespostaController {

    @Autowired
    private ItemRespostaRepository itemRespostaRepository;
    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private JustificativaItemRespostaRepository repository;

    @RequestMapping("justificativa")
    public String justificativaItem(ItemResposta itemResposta, Model model) {
		Long id = itemResposta.getId();
		System.out.println(id);
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
        Usuario u = (Usuario) request.getSession().getAttribute("usuarioLogado");
        justificativaItemResposta.setUsuario(u);

        //pega o itemResposta que esta sendo usado e insere na justificativa

        repository.save(justificativaItemResposta);

        return "redirect:/";
    }

}
