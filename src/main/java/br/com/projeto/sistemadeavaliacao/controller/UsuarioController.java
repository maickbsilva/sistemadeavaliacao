package br.com.projeto.sistemadeavaliacao.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import br.com.projeto.sistemadeavaliacao.repository.PesquisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("cadastro")
	private String formularioCadastro(Model model) {
		return "cadastro/cadastroUsuario";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr,
			TipoUsuario tipo) {

		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "verificar os campos novamente...");
			return "redirect:cadastro";

		}

		if (usuario.getSenha().equals(HashUtil.hash(""))) {

			String hash = repository.findById(usuario.getUserId()).get().getSenha();
			usuario.setSenha(hash);
		}

		try

		{
			repository.save(usuario);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso ID:" + usuario.getUserId());
		} catch (Exception e) {
			System.out.println("erro ao cadastrar");
			attr.addFlashAttribute("mensagemErro", "Verificar os campos novamente, Este Nif já existe");
		}
		return "redirect:cadastro";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("lista/{page}")
	public String lista(Model model, @PathVariable("page") int page) {

		PageRequest pageble = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));

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

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("alterar")
	public String alterar(Long userId, Model model) {
		Usuario usuario = repository.findById(userId).get();
		model.addAttribute("users", usuario);
		return "forward:cadastro";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("excluir")
	public String excluir(Long userId) {
		Usuario excluir = repository.findById(userId).get();
		repository.delete(excluir);
		return "redirect:lista/1";
	}

	@DiretorAnnotation
	@RequestMapping("telaInicialDiretor")
	public String telaInicialDiretor() {
		return "telainicial/telaInicialDiretor";
	}

	@DocenteAnnotation
	@RequestMapping("telaInicialDocencia")
	public String telaInicialDocencia(Model model, HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuarioLogado");
		Long id = u.getUserId();
		model.addAttribute("pesq", pesquisaRepository.listaPesquisaPorDocente(id));
		return "telainicial/telaInicialDocencia";
	}

	@SecretariaAnnotation
	@RequestMapping("telaInicialSecretaria")
	public String telaInicialSecretaria() {
		return "telainicial/telaInicialSecretaria";
	}

	@PublicoAnnotation
	@RequestMapping("login")
	public String login(Usuario admLogin, RedirectAttributes attr, HttpSession session, Model model) {

		Usuario user = repository.findByNifAndSenha(admLogin.getNif(), admLogin.getSenha());

		if (user == null) {
			

			/*!Mensagem de erro de Usuario NULO
			!
			!
			*/
			attr.addFlashAttribute("mensagemErro", "verificar os campos novamente...");	
			return "redirect:/";	
		} else {
			//trocando a senha caso usuario esteja com a senha padrao
			if (user.getSenha().equals(HashUtil.hash("sistema"))) {
				model.addAttribute("usuario", user);
				return "util/Model";

			}

			if (user.getTipo() != null && user.getTipo() == TipoUsuario.DIRETOR) {
				session.setAttribute("usuarioLogado", user);
				session.setAttribute("nivel", user.getTipo());
				return "redirect:/telaInicialDiretor";

			} else if (user.getTipo() != null && user.getTipo() == TipoUsuario.SECRETARIA) {
				session.setAttribute("usuarioLogado", user);
				session.setAttribute("nivel", user.getTipo());
				return "redirect:/telaInicialSecretaria";
			}else if (user.getTipo() != null && user.getTipo() == TipoUsuario.DOCENCIA){
				session.setAttribute("usuarioLogado", user);
				session.setAttribute("nivel", user.getTipo());
				return "redirect:/telaInicialDocencia";
			}
		}
		return "login/login";

	}

	
	@RequestMapping(value = "buscarUser", method = RequestMethod.GET)
	public String buscar(String buscar, Model model){
		model.addAttribute("users", repository.Buscar(buscar));
		return "listas/listaBuscaUsuario";
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login/login";

	}
	
	@RequestMapping("/")
	public String acesso() {
		return "login/login";
	}
	
	@SecretariaAnnotation
	@RequestMapping("reseta")
	public String reseta(Long userId, Model model) {
		Usuario usuario = repository.findById(userId).get();
		String sistema = "sistema";
		usuario.setSenha(sistema);
		repository.save(usuario);
		System.out.println(usuario.getSenha() + "Senha alterada");
		// alert senha redefinida...

		// redirecionar mensagem senha de usuario alterada
		return "redirect:lista/1";
	}
	
	// --//--//
	@RequestMapping(value = "alteraSenha", method = RequestMethod.POST)
	private String formularioSenha(Long userId, String senha) {
		System.out.println(userId + senha);

		Usuario oldUsuario = repository.findById(userId).get(); // Erro id Null

		oldUsuario.setSenha(senha);

		repository.save(oldUsuario);
		return "login/login";
	}


}
