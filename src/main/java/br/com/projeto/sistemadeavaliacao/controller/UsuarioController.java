package br.com.projeto.sistemadeavaliacao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
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

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("cadastro")
	private String formularioCadastro(Model model) {
		return "cadastro/cadastroUsuario";
	}

	/*
	 * Salvar Usuarios
	 */
	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("save")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr,
			TipoUsuario tipo) {

		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "verificar os campos nvoamente...");
			return "redirect:cadastro";

		}

		// verificando a senha
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

	/*
	 * Listagem dos Cadastros
	 */
	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("lista/{page}")
	public String lista(Model model, @PathVariable("page") int page) {
		// criar uma pageble para informar os parametros da pagina
		PageRequest pageble = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		// criando lista

		Page<Usuario> pagina = repository.findAll(pageble);
		// add a model a lista

		model.addAttribute("usuario", pagina.getContent());
		// gerar total de paginas
		int totalPaginas = pagina.getTotalPages();
		// vetor para lista
		List<Integer> numPaginas = new ArrayList<Integer>();
		// prenchendo as lista

		for (int i = 1; i <= totalPaginas; i++) {
			numPaginas.add(i);
		}
		// fazendo a model para valores serem add

		model.addAttribute("numPagina", numPaginas);
		model.addAttribute("totalPages", totalPaginas);
		model.addAttribute("pagAtual", page);

		return "listas/listaUsuarios";

	}

	/*
	 * Alterar Usuarios
	 */
	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("alterar")
	public String alterar(Long userId, Model model) {
		Usuario usuario = repository.findById(userId).get();
		model.addAttribute("users", usuario);
		return "forward:cadastro";
	}

	/*
	 * Excluir Usuario
	 */
	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("excluir")
	public String excluir(Long userId) {
		Usuario excluir = repository.findById(userId).get();
		repository.delete(excluir);
		return "redirect:lista/1";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("telaInicialDiretor")
	public String telaInicialDiretor() {
		return "telainicial/telaInicialDiretor";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("telaInicialDocencia")
	public String telaInicialDocencia() {
		return "telainicial/telaInicialDocencia";
	}

	@SecretariaAnnotation
	@DiretorAnnotation
	@RequestMapping("telaInicialSecretaria")
	public String telaInicialSecretaria() {
		return "telainicial/telaInicialSecretaria";
	}

	
	  //Login
	  @PublicoAnnotation
	  @RequestMapping("login") public String login(Usuario admLogin,
	 RedirectAttributes attr, HttpSession session) { // buscar o adm no banco
	  System.out.println(admLogin.getNif());
	  System.out.println(admLogin.getSenha());
	  
	  Usuario user = repository.findByNifAndSenha(admLogin.getNif(), admLogin.getSenha()); 
	  //verificar se existe 
	  if (user == null){
	  System.out.println("usuario não existe");
	  attr.addFlashAttribute("mensagemErro", "Login ou Senha invalida(s)"); 
	  return "login/login";
	  
	  } else { System.out.println("usuario existe"); // salva o adm na sessão
	  session.setAttribute("usuarioLogado", user);
	  
	  return "redirect:/lista/1"; }
	  
	}
	  @RequestMapping("logout") 
	  
	  public String logout(HttpSession session) {
	  //invalida a sessão 
		  session.invalidate(); 
	  // voltar a pagina inicial redirect pagina inical 
	  return "login/login";
	  
	  }
	  
	  
	}
