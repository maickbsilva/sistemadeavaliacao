package br.com.projeto.sistemadeavaliacao.controller;

import br.com.projeto.sistemadeavaliacao.model.ItemResposta;
import br.com.projeto.sistemadeavaliacao.model.Pergunta;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import br.com.projeto.sistemadeavaliacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.DocenteAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("itemResposta/")
public class ItemRespostaController {

    @Autowired
    private ItemRespostaRepository itemRespostaRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;
    @RequestMapping("justificativa")
    public String justificativaItem(ItemResposta itemResposta, Model model) {
		Long id = itemResposta.getId();
		System.out.println(id);
        Pergunta idPerg = itemRespostaRepository.findPergunta(id);
        model.addAttribute("item", itemRespostaRepository.findById(id).get());
        model.addAttribute("pergunta", perguntaRepository.findById(idPerg.getId()).get());
        return "justificativa/justificativaItem";

    }

    @RequestMapping(value = "novaJustiItem", method = RequestMethod.POST)
    public String novaJustificativa(HttpServletRequest request, HttpSession session) {
//        Date data = new Date();
        System.out.println(session.getAttributeNames());

        return "redirect:/";
    }

}
