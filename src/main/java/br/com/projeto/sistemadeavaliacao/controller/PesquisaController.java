package br.com.projeto.sistemadeavaliacao.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.projeto.sistemadeavaliacao.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.DocenteAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.Pesquisa;
import br.com.projeto.sistemadeavaliacao.repository.CursoRepository;
import br.com.projeto.sistemadeavaliacao.repository.ItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PerguntaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import br.com.projeto.sistemadeavaliacao.repository.RespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.UsuarioRepository;

@Controller

@RequestMapping("pesquisa/")
public class PesquisaController {

    @Autowired
    private PesquisaRepository pesquisaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemRespostaRepository itemRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @SecretariaAnnotation
    @DiretorAnnotation
    @RequestMapping("cadastrar")
    public String cadPesquisa(Model model) {
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("respostas", respostaRepository.findAll());
        model.addAttribute("usuario", usuarioRepository.BuscarDocentes());
        model.addAttribute("pergunta", perguntaRepository.perguntasGerais());
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        model.addAttribute("tomorrow", dt);

        return "pesquisa/cadPesquisa";
    }

    @SecretariaAnnotation
    @DiretorAnnotation
    @RequestMapping("buscar")
    public String buscaPesquisa(Long id, Model model) {
        model.addAttribute("pesq", pesquisaRepository.findById(id).get());
        return "pesquisa/listaPesquisa";
    }

    @SecretariaAnnotation
    @DiretorAnnotation
    @DocenteAnnotation
    @RequestMapping("listarResposta")
    public String listarRespota(Long id, Model model) {
        model.addAttribute("pesq", pesquisaRepository.findById(id).get());
        model.addAttribute("perg", perguntaRepository.findAll());
        model.addAttribute("item", itemRepository.findAll());
        model.addAttribute("resposta", respostaRepository.findAll());
        Long contagem = respostaRepository.contaResposta(id);
        model.addAttribute("contagem", contagem);
        return "pesquisa/listaResposta";

    }

    @SecretariaAnnotation
    @DiretorAnnotation
    @RequestMapping(value = "novaPesquisa", method = RequestMethod.POST)
    public String novaPesquisa(Pesquisa pesquisa, String listaDocentes, RedirectAttributes attr, Model model) {
        List<Usuario> l = pesquisa.getListaDocentes();
        if (pesquisa.getListaDocentes() != null) {
            for (int i = 0; i < pesquisa.getListaDocentes().size(); i++) {
                if (l.get(i).getUserId() == pesquisa.getUsuarioDocente().getUserId()) {
                    l.remove(i);
                }
            }
        }
        String p = pesquisa.getTurma().toUpperCase();
        pesquisa.setTurma(p);
        pesquisaRepository.save(pesquisa);
        attr.addFlashAttribute("msgSucess", "O Codigo da Nova Pesquisa é:" + pesquisa.getId());

        Long id = pesquisa.getId();
        String nome = String.valueOf(pesquisa.getUsuarioDocente().getNome());
        attr.addFlashAttribute("idpesq", id);
        attr.addFlashAttribute("nomeDocente", nome);
        return "redirect:cadastrar";
    }

    @SecretariaAnnotation
    @DiretorAnnotation
    @RequestMapping("listar/{page}")
    public String listaPesquisa(Model model, @PathVariable("page") int page) {

        PageRequest pageable = PageRequest.of(page - 1, 15, Sort.by(Sort.Direction.DESC, "id"));

        Page<Pesquisa> pagina = pesquisaRepository.findAll(pageable);

        model.addAttribute("pesq", pagina.getContent());

        int totalPages = pagina.getTotalPages();

        List<Integer> numPaginas = new ArrayList<Integer>();

        for (int i = 1; i <= totalPages; i++) {
            numPaginas.add(i);
        }
        model.addAttribute("numPaginas", numPaginas);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pagAtual", page);

        return "pesquisa/listaPesquisa";
    }
}
