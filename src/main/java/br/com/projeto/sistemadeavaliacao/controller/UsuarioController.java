package br.com.projeto.sistemadeavaliacao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import br.com.projeto.sistemadeavaliacao.repository.JustificativaItemRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.JustificativaRespostaRepository;
import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.DocenteAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.PublicoAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.TipoUsuario;
import br.com.projeto.sistemadeavaliacao.model.Usuario;

import br.com.projeto.sistemadeavaliacao.repository.UsuarioRepository;
import br.com.projeto.sistemadeavaliacao.util.HashUtil;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PesquisaRepository pesquisaRepository;

    @Autowired
    private JustificativaItemRespostaRepository justificativaItemRespostaRepository;

    @Autowired
    private JustificativaRespostaRepository justificativaRespostaRepository;

    @DiretorAnnotation
    @SecretariaAnnotation
    @RequestMapping("cadastro")
    private String formularioCadastro(Model model) {
        return "cadastro/cadastroUsuario";
    }

    @DiretorAnnotation
    @SecretariaAnnotation
    @DocenteAnnotation
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String salvarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr, TipoUsuario tipo,
                                Model model) {

        String nome = usuario.getNome().toUpperCase();
        usuario.setNome(nome);
        model.addAttribute("nomeUsuario", nome);

        if (usuario.getSenha().equals(HashUtil.hash(""))) {

            String hash = repository.findById(usuario.getUserId()).get().getSenha();
            usuario.setSenha(hash);
        }

        try {
            repository.save(usuario);
//            attr.addFlashAttribute("mensagemSucesso", "Usu??rio cadastrado com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("mensagemErro", "NIF j?? existente, tente outro.");
        }

        Long id = usuario.getUserId();
        attr.addFlashAttribute("iduser", id);

        return "redirect:cadastro";
    }

    @DiretorAnnotation
    @SecretariaAnnotation
    @RequestMapping("lista/{page}")
    public String lista(Model model, @PathVariable("page") int page) {

        PageRequest pageble = PageRequest.of(page - 1, 15, Sort.by(Sort.Direction.DESC, "ativo"));

        Page<Usuario> pagina = repository.findAll(pageble);

        model.addAttribute("usuario", pagina.getContent());

        int totalPaginas = pagina.getTotalPages();

        List<Integer> numPaginas = new ArrayList<Integer>();

        for (int i = 1; i <= totalPaginas; i++) {
            numPaginas.add(i);
        }

        model.addAttribute("numPagina", numPaginas);
        model.addAttribute("totalPages", totalPaginas);
        model.addAttribute("pagAtual", page);

        return "listas/listaUsuarios";

    }

    @DiretorAnnotation
    @SecretariaAnnotation
    @RequestMapping("alterar")
    public String alterar(Long userId, Model model) {
        Usuario usuario = repository.findById(userId).get();
        model.addAttribute("users", usuario);
        return "forward:cadastro";
    }

    @DiretorAnnotation
    @SecretariaAnnotation
    @RequestMapping("desativar")
    public String desativar(Long userId, RedirectAttributes attr) {
        Usuario u = repository.findById(userId).get();
        u.setAtivo(false);
        repository.save(u);
        attr.addFlashAttribute("desativa", u.isAtivo());
        return "redirect:lista/1";
    }

    @DiretorAnnotation
    @RequestMapping("telaInicialDiretor")
    public String telaInicialDiretor(Model model) {
        model.addAttribute("justItem", justificativaItemRespostaRepository.findAllByOrderByIdDesc());
        model.addAttribute("justResp", justificativaRespostaRepository.findAllByOrderByIdDesc());
        return "telainicial/telaInicialDiretor";
    }

    @DocenteAnnotation
    @RequestMapping("telaInicialDocencia")
    public String telaInicialDocencia(Model model, HttpServletRequest request) {
        Usuario u = (Usuario) request.getSession().getAttribute("docenciaLogado");
        Long id = u.getUserId();
		System.out.println(id);
        model.addAttribute("pesq", pesquisaRepository.listaPesquisaPorDocente(id));
        return "telainicial/telaInicialDocencia";
    }

    @SecretariaAnnotation
    @RequestMapping("telaInicialSecretaria")
    public String telaInicialSecretaria() {
        return "telainicial/telaInicialSecretaria";
    }

    @PublicoAnnotation
    @DocenteAnnotation
    @DiretorAnnotation
    @SecretariaAnnotation
    @PostMapping("login")
    public String login(Usuario admLogin, RedirectAttributes attr, HttpSession session, Model model) {

        Usuario user = repository.findByNifAndSenha(admLogin.getNif(), admLogin.getSenha());

        if (user == null) {
            attr.addFlashAttribute("mensagemErro", "NIF e/ou senha incorreto(s).");
            return "redirect:/login";
        } else if (!user.isAtivo()) {
            attr.addFlashAttribute("mensagemErro", "Esse usuario foi desativado, entre em contato com o administrador");
            return "redirect:/login";
        }

        //trocando a senha caso usuario esteja com a senha padrao
        if (user.getSenha().equals(HashUtil.hash("sistema"))) {
            model.addAttribute("usuario", user);
            return "util/Model";
        }

        if (user.getTipo() != null && user.getTipo() == TipoUsuario.DIRETOR) {
            session.setAttribute("diretoriaLogado", user);
            session.setAttribute("nivel", user.getTipo());
            return "redirect:/telaInicialDiretor";

        } else if (user.getTipo() != null && user.getTipo() == TipoUsuario.SECRETARIA) {
            session.setAttribute("secretariaLogado", user);
            session.setAttribute("nivel", user.getTipo());
            return "redirect:/telaInicialSecretaria";

        } else if (user.getTipo() != null && user.getTipo() == TipoUsuario.DOCENCIA) {
            session.setAttribute("docenciaLogado", user);
            session.setAttribute("nivel", user.getTipo());
            return "redirect:/telaInicialDocencia";
        }

        return "login/login";
    }

    @DiretorAnnotation
    @SecretariaAnnotation
    @GetMapping("buscarUser")
    public String buscar(String nome, Model model) {
        model.addAttribute("users", repository.Buscar(nome));

        return "listas/buscarUser";
    }

    @PublicoAnnotation
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login/login";
    }

    @PublicoAnnotation
    @RequestMapping("/login")
    public String acesso() {
        return "login/login";
    }

    @PublicoAnnotation
    @RequestMapping("/")
    public String form() {
        return "resposta/codigoPesquisa";
    }

    @DiretorAnnotation
    @SecretariaAnnotation
    @DocenteAnnotation
    @RequestMapping("reseta")
    public String reseta(Long userId, Model model) {
        Usuario usuario = repository.findById(userId).get();
        String sistema = "sistema";
        usuario.setSenha(sistema);
        repository.save(usuario);

        // redirecionar mensagem senha de usuario alterada
        return "redirect:lista/1";
    }

    @DiretorAnnotation
    @SecretariaAnnotation
    @DocenteAnnotation
    @PublicoAnnotation
    @RequestMapping(value = "alteraSenha", method = RequestMethod.POST)
    private String formularioSenha(Long userId, String senha) {
        Usuario oldUsuario = repository.findById(userId).get(); // Erro id Null
        oldUsuario.setSenha(senha);
        repository.save(oldUsuario);

        return "login/login";
    }

    @PublicoAnnotation
    @RequestMapping("/acessoNegado")
    public String acessoNegado() {
        return "acessoNegado";
    }

    @PublicoAnnotation
    @RequestMapping("/util/Model")
    public String model() {
        return "util/Model";
    }

}
