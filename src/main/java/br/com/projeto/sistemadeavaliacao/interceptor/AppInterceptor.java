package br.com.projeto.sistemadeavaliacao.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.projeto.sistemadeavaliacao.annotation.DiretorAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.DocenteAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.PublicoAnnotation;
import br.com.projeto.sistemadeavaliacao.annotation.SecretariaAnnotation;
import br.com.projeto.sistemadeavaliacao.model.TipoUsuario;

@Component
public class AppInterceptor implements HandlerInterceptor {
	
	
@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		// variavel para obter a URI
		String uri = request.getRequestURI();

		// variavel para a sessao
		HttpSession session = request.getSession();
		/*HttpSession sessionDi = request.getSession();
		HttpSession sessionD = request.getSession();*/
		
		if (uri.startsWith("/error")) {
			System.out.println("URI");
			return true;
		}

		if (handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			System.out.println("Passou aqui 1 !");	
			if (uri.startsWith("/")) {
				System.out.println("Passou aqui 2 !");
				System.out.println("Session 	" + session.getAttribute("nivel"));
				
			if (metodo.getMethodAnnotation(SecretariaAnnotation.class) != null){
				System.out.println("Passou aqui 3 !");
				if (session.getAttribute("nivel") == TipoUsuario.SECRETARIA) {
					System.out.println("Passou aqui SECRETARIA if else !");
					return true;
				}
				
				if (TipoUsuario.SECRETARIA == null) {
					response.sendError(HttpStatus.UNAUTHORIZED.value());
				} else {
					response.sendError(HttpStatus.FORBIDDEN.value());
				}
				return false;
		
			} else if (metodo.getMethodAnnotation(DocenteAnnotation.class) != null) {
				System.out.println("Passou aqui 5 !");
				if (session.getAttribute("nivel") == TipoUsuario.DOCENCIA) {
					System.out.println("Passou aqui Docente if else !");
					return true;
				}
				
				if (TipoUsuario.DOCENCIA == null) {
					response.sendError(HttpStatus.UNAUTHORIZED.value());
				} else {
					response.sendError(HttpStatus.FORBIDDEN.value());
				}
				return false;

			} else if (metodo.getMethodAnnotation(DiretorAnnotation.class) != null) {
				System.out.println("Passou aqui 5 !");
				if (session.getAttribute("nivel") == TipoUsuario.DIRETOR) {
					System.out.println("Passou aqui Diretor if else !");
					return true;
				}
				
				if (TipoUsuario.DIRETOR == null) {
					response.sendError(HttpStatus.UNAUTHORIZED.value());
				} else {
					response.sendError(HttpStatus.FORBIDDEN.value());
				}
				return false;

			}
			
			return true;

			} else {
				System.out.println("Passou aqui !");

				if (metodo.getMethodAnnotation(PublicoAnnotation.class) != null) {
					return true;
				}
				if (session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				response.sendRedirect("/");
				return false;

			}

		}
		return false;

	}
}
				
